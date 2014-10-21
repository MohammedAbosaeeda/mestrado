package keso.ray;

import keso.core.Task;
import keso.core.Memory;
import keso.core.MemoryService;
import test.DebugOut;
import java.lang.Math;

public class Tracer extends Task {

	private static final double objs[] = {
		/* 11 objects x , y, z, r, ... */
		0. ,0., -200.5, 40000.,
	       	0., 0., 0., 0.25,
		0.272166, 0.272166, 0.544331, .027777,
		0.643951, 0.172546, 0., .027777,
		0.172546, 0.643951, 0., .027777,
		-0.371785, 0.099620, 0.544331, .027777, 
		-0.471405, 0.471405, 0., .027777,
		-0.643951,-0.172546, 0., .027777,
		0.099620,-0.371785, 0.544331, .027777,
		-0.172546,-0.643951, 0., .027777,
		0.471405,-0.471405, 0., .027777,
	};

	
	private static final double lights[] = {
		/* 3 lights x,y,z,... */
		4., 3., 2., 1., -4., 4., -3., 1., 5.
	};

	private static final double move[] = {
		-0.2,-0.2,-0.0,
		-0.2,-0.2,-0.0,
		-0.1,-0.1,-0.1,
		-0.0,-0.0,-0.3,
		-0.0,-0.0,-0.3,
		-0.0,-0.0,-0.3,
		-0.0,-0.3,-0.3,
		-0.0,-0.3,-0.0,
		 0.2, 0.2, 0.0,
		 0.2, 0.2, 0.0,
		 0.1, 0.1, 0.1,
		 0.0, 0.0, 0.3,
		 0.0, 0.0, 0.3,
		 0.0, 0.0, 0.3,
		 0.0, 0.3, 0.3,
		 0.0, 0.3, 0.0,
	};

	static class Color {
		double r;
		double g;
		double b;
	}

	static class MaxP {
		double max;
	}

	static double nsqrt(double c) {
		double t = c; 
		double EPSILON = 1e-4;

		if (c < 1.0) {
			while (Math.abs(t - c/t) > t*EPSILON) {
				t = (c/t + t) / 2.0;
			}
		} else {
			while ((t - c/t) > t*EPSILON) {
				t = (c/t + t) / 2.0;
			}
		}

		return t;
	}

	/*
	static double nsqrt(double c) {
		double t = c;              // estimate of the square root of c
		double EPSILON = 1e-4;    // relative error tolerance

		// repeatedly apply Newton update step
		// until desired precision is achieved
		while (Math.abs(t - c/t) > t*EPSILON) {
			t = (c/t + t) / 2.0;
		}

		return t;
	}
	*/

	static double npow(double v, int n) {
		for (int i=0;i<n;i++) v*=v;
		return v;
	}

	static int intersect(double x, double y, double z, double dx, double dy, double dz, MaxP maxp) {
		int i,o=0,oo=-1;
		double max = maxp.max;
		double xx, yy, zz, b, t;

		for (i = 0; i < 11; i++) 
		{
			xx = objs[o++] - x; yy = objs[o++] - y; zz = objs[o++] - z;
			b = xx * dx + yy * dy + zz * dz;
			if ((t = b * b - xx * xx - yy * yy - zz * zz + objs[o++]) < 0
				       	|| (t = b-nsqrt(t)) < 1e-6 || t > max)
				continue;
			oo = o - 4;
			max = t;
		}

		maxp.max = max;
		return oo;
	}

	static boolean intersect_fast(double x, double y, double z, double dx, double dy, double dz) {
		int i,o=0;
		double xx, yy, zz, b, t;

		for (i = 0; i < 11; i++) 
		{
			xx = objs[o++] - x; yy = objs[o++] - y; zz = objs[o++] - z;
			b = xx * dx + yy * dy + zz * dz;
			if ((t = b * b - xx * xx - yy * yy - zz * zz + objs[o++]) < 0
				       	|| (t = b-nsqrt(t)) < 1e-6 || t > 1e6)
				continue;
			return true;
		}

		return false;
	}

	static final MaxP maxp = new MaxP();
	static final MaxP ld = new MaxP();

	private static Color shade_intern(Color col, double x, double y, double z, double dx, double dy, double dz, int de) {
		double c = .14433, l,  k;
		double nx, ny, nz, ldx, ldy, ldz, rdx, rdy, rdz;
		int i,oi;

		maxp.max = 1e6;
		if (-1==(oi=intersect(x, y, z, dx, dy, dz, maxp)))
		{
			col.r += 20; col.g += 92; col.b += 191;
			return col;
		}

		x += maxp.max * dx; y += maxp.max * dy; z += maxp.max * dz;
		nx = x - objs[oi++]; ny = y - objs[oi++]; nz = z - objs[oi++];
		l = nsqrt(nx * nx + ny * ny + nz * nz);
		nx /= l; ny /= l; nz /= l;
		k = nx * dx + ny * dy + nz * dz;
		rdx = dx - 2 * k * nx; rdy = dy - 2 * k * ny; rdz = dz - 2 * k * nz;
	
		int o=0;	
		for (i = 0; i < 3; i++)
		{
			ldx = lights[o++] - x; ldy = lights[o++] - y; ldz = lights[o++] - z;
			l = nsqrt(ldx * ldx + ldy * ldy + ldz * ldz);
			ldx /= l; ldy /= l; ldz /= l;
			//if (-1!=intersect(x, y, z, ldx, ldy, ldz, ld))
			if (intersect_fast(x, y, z, ldx, ldy, ldz))
				continue;

			if ((l = ldx * nx + ldy * ny + ldz * nz) < 0)
				continue;

			c += l;
			if ((l = rdx * ldx + rdy * ldy + rdz * ldz) > 0)
				c += 4 * npow(l, 6);
		/*	
			if ((l = rdx * ldx + rdy * ldy + rdz * ldz) > 0)
				c += 4 * Math.pow(l, 40.);
				*/
		}

		if (oi != 3) {
			col.r += c * 74; col.g += c * 66; col.b += c * 52;

			if (de < 3) {
				col.r *= 2; col.g *= 2.22; col.b *= 2.86;
				col = shade_intern(col, x, y, z, rdx, rdy, rdz, de + 1);
				col.r /= 2; col.g /= 2.22; col.b /= 2.86;
			}

		} else {
			col.r += c * 118; col.g += c * 88; col.b += c * 39;
		}

		return col;
	}

	public void put_pixel(Memory gmem, int mode, int line_len, int x, int y, Color col) {
		int p,c,r,g,b;

		b = ((int)col.r<0?0:(int)col.r);
		g = ((int)col.g<0?0:(int)col.g);
		r = ((int)col.b<0?0:(int)col.b);

		r = (r>255?255:r);
		g = (g>255?255:g);
		b = (b>255?255:b);

		switch (mode) {
			case 15:
				c = ((r>>3) & 0x1f);
				c = ( c << 5 ) | ((g>>3) & 0x1f);
				c = ( c << 5 ) | ((b>>3) & 0x1f);

				p = ((y*line_len) + x) << 1;
				gmem.set16(p, (short)c);

				break;

			case 16:
				c = ((r>>3) & 0x1f);
				c = ( c << 6 ) | ((g>>2) & 0x3f);
				c = ( c << 5 ) | ((b>>3) & 0x1f);

				p = ((y*line_len) + x) << 1;
				gmem.set16(p, (short)c);

				break;

			case 24:
				p = ((y*line_len) + x) * 3;
				gmem.set8(p, (byte)r);p++;
				gmem.set8(p, (byte)g);p++;
				gmem.set8(p, (byte)b);
		}
	}

	public void launch() {
		Color col = new Color();

		int w = 640;
		int h = 480;
		int mode = 16;

		//Memory gmem = MemoryService.allocDynamicDeviceMemory(0xA0000, (w*h*3));
		Memory gmem = MemoryService.allocStaticDeviceMemory(0xE0000000, 8*1024*1024);
		Memory buffer = MemoryService.allocStaticMemory(640*480*2);

		int p = 0;
		int pw = 200;
		int ph = 200;
		//int pw = 480;
		//int ph = 480;
		int i=0;

		double vx, vy, vz;
		vx=2.1; vy=1.3; vz=1.7;
		MemoryService.copy(buffer,0,gmem,0,640*480*2);
		/*
		for (int j=0;j<640*480*2;j++) {
			gmem.set8(j,buffer.get8(j));
		}
		*/
		while (true) {
			benchmark.Timer.startPoint("shade");
			for (int y=0;y<ph;y++) {
				double yy = 1. - y / (double)(ph-1.0);
				double dz = -2.72303                 + yy * 2.04606;
				for (int x=0;x<pw;x++) {
					double xx = x / (double)(pw-1.0);
					double dx = -0.847569 - xx * 1.30741 - yy * 1.19745;
					double dy = -1.98535  + xx * 2.11197 - yy * 0.741279;
					double l = nsqrt(dx * dx + dy * dy + dz * dz);

					col.r=0.0; col.g=0.0; col.b=0.0; 
					col = shade_intern(col, vx, vy, vz, dx / l, dy / l, dz / l, 0);

					put_pixel(buffer, 16, 640, x+80+140, y+140, col);
				}
			}
			benchmark.Timer.endPoint("shade");
			MemoryService.copy(buffer,0,gmem,0,640*480*2);
			/*
			for (int j=0;j<640*480*2;j++) {
				gmem.set8(j,buffer.get8(j));
			}
			*/
			vx+=move[i++]; vy+=move[i++]; vz+=move[i++];;
			if (i==move.length) i=0;

			benchmark.Timer.report();
		}

		//return ;
	}
}
