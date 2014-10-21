/*
 * This file is a test to debug the call to the calculation of the position
 * of a node. It uses a simplified version of the data structure of
 * HECOPS@EPOS.
 *
 * How to use it:
 * In location_algorithm.cc, comment the block of code that is the
 * implementation of HECOPS@EPOS and uncomment the block for calculation.
 * Compared to the implementation of HECOPS, the test for calculation
 *  - doesn't use the same structure of messsage (it uses a simplified one)
 *  - presents a simplified behavior for the anchors
 *  - requires a very specific configuration described below
 *
 * This test describes the following physical configuration:
 *
 *         ^
 *         |
 * (0,120) o
 *         |
 *         |
 *         |
 *         |
 *         |       m(x,y)
 *         |
 *         |
 *         |
 *         o---------------o------->
 *    (0,0)             (90,0)
 *
 *
 *  To perform this test, you will need 4 motes: 3 anchors and one mobile.
 *  All you need to do is to change N_SENDER to the desired value before
 *  you upload the program on the mote (-1, 0, 1, 2 for instance). You shall
 *  then place the motes in the above-described position.
 *  The calculation of the position of the mobile node won't begin until
 *  it receives the position of the 3 anchors (NB: because of collision, you
 *  might want to plug the anchors one by one instead of having them all
 *  sending their position at the same time).
 *
 *  ===========================================================================
 *  === Bugs log
 *  ===========================================================================
 *
 *  16/07/13
 *  The result of the calculation appears to be unstable.
 *
 *  Example of result:
 *  Input of calculation function:
 *  id|x |y  | rssi
 *  0 |0 | 0 | -56
 *  1 |0 |120| -67
 *  2 |90| 0 | -42
 *
 *  Output of the calculation function:
 *  (52,42)
 *
 *  => Not very accurate, but acceptable
 *
 *  Example of result:
 *  Input of calculation function:
 *  id|x |y  | rssi
 *  0 |0 | 0 | -56
 *  1 |0 |120| -67
 *  2 |90| 0 | -57
 *
 *  Output of the calculation function:
 *  (0,0)
 *
 *  => A small variation of one RSSI value wronged the result
 */


#include <utility/ostream.h>
#include <location_algorithm.h>
#include <node.h>

__USING_SYS
#define N_SENDER -1
extern "C" { void _exit(int s); }

struct Msg {
    int x;
    int y;
    int id;
    Msg(int x, int y, int id): x(x),y(y),id(id) {} ;
} ;


NIC * nic;
OStream cout;
RSSI* node;
HECOPS* hecops;

template <class T>
void calculate(T x[], T y[], T d[], int n){
    T xmax, xmin, ymin, ymax, xi, xf, yi, yf;


    kout << "!!! In calculate \n";
    for (int i=0; i<n; i++) {
        kout << i<<":(" << x[i] << ", " << y[i] << ") / " << d[i] << endl ;
    }

    xf = xmax = x[0]+d[0];
    xi = xmin = x[0]-d[0];
    yf = ymax = y[0]+d[0];
    yi = ymin = y[0]-d[0];

    kout << "xf=xmax" << xf << endl;
    kout << "xi=xmin" << xi << endl;
    kout << "yf=ymax" << yf << endl;
    kout << "yi=ymin" << yi << endl;
    kout << "__________" << endl;

    for(int i=1; i<n; i++){
        T   xp = x[i]+d[i], yp = y[i]+d[i],
                xn = x[i]-d[i], yn = y[i]-d[i];

        kout << "xp=" << xp << endl;
        kout << "yp=" << yp << endl;
        kout << "xn=" << xn << endl;
        kout << "yn=" << yn << endl;

        // �REA M�XIMA
        xmin = Math::min(xmin, xn);   xmax = Math::max(xmax, xp);
        ymin = Math::min(ymin, yn);   ymax = Math::max(ymax, yp);

        // INTERSEC�AO
        xi = Math::max(xi, xn);       xf = Math::min(xf, xp);
        yi = Math::max(yi, yn);       yf = Math::min(yf, yp);

        kout << "xmin=" << xmin << endl;
        kout << "xmax=" << xmax << endl;
        kout << "ymin=" << ymin << endl;
        kout << "ymax=" << ymax << endl;
        kout << "xi=" << xi << endl;
        kout << "xf=" << xf << endl;
        kout << "yi=" << yi << endl;
        kout << "yf=" << yf << endl;

        if(xi >= xf) { xi = xmin; xf = xmax; }
        if(yi >= yf) { yi = ymin; yf = ymax; }

        kout << "xi=" << xi << endl;
        kout << "xf=" << xf << endl;
        kout << "yi=" << yi << endl;
        kout << "yf=" << yf << endl;

        kout << "____________" << endl;
    }

    int N = 2000, w=0;
    T xe, ye, nx, ny;
    T emin = 0xffff, e, lx = xf-xi, ly = yf-yi, dx, dy;
#ifndef _EPOS_
    if(lx == 0 || ly == 0){
        cout << "Division by zero";
        _exit(1);
    }
#endif
    kout << "\n\nlx/ly = " << lx << "/" << ly << endl << endl;
    nx = Math::isqrt(N*lx/ly);
    ny = Math::isqrt(N*ly/lx);
#ifndef _EPOS_
    if(nx == 0 || ny == 0){
        cout << "Division by zero";
        _exit(2);
    }
#endif
    dx = lx / nx;  dy = ly / ny;
    if(dx==0){ dx = 1; nx = xf-xi; }
    if(dy==0){ dy = 1; ny = yf-yi; }

    T resx, resy;
    for(int i=0; i<nx;){
        for(int j=0; j<ny;){
            xe = ((T)i*dx) + xi;
            ye = ((T)j*dy) + yi;
            e=0;
            for(int k=0; k<n; k++)
                e += Math::abs(Math::distance(x[k],y[k],xe,ye)-d[k]);
            if(e < emin){
                emin = e;
                resx = xe; resy = ye;
                //printf("| x=%l y=%l e=%l ", result.x, result.y, e);
            }
            w++;
            while((++j*dy)+yi == ye);
        }
        while((++i*dx)+xi == xe);
        kout << "resx=" << resx << endl;
        kout << "resy=" << resy << endl;
    }

    kout << "\n I calculated " << resx << "," << resy << endl;
}


int main() {
    /*** To put on one node to check if calculations are correct */

    //negative sqrt
    //long x[2] = {225,0}, y[2] = {90,0}, rssi[2] = {-81,-72};
    // calculate(x,y,rssi,2);

    //huge values
    //note: when run on a mote through HECOPS@EPOS, returns something around
    //      (4000000,4000000)
    //      when run on a mote just like that, returns 0,0
    //but this is probably the same problem
    long x[3] = {0,45,45}, y[3] = {0,-45,45}, rssi[3] = {-57,-53,-54};
    calculate(x,y,rssi,3);

    /*
    nic = new NIC();
    hecops = new HECOPS();

    switch (N_SENDER) {
        case 0 :
        {
            cout << "Creation of an anchor node 'A' in (0,0)\n";
            node = new RSSI(new Anchor('A',0,0, hecops, nic));
            Msg m(0,0,0);
            while(true){
                    node->nic->send(NIC::BROADCAST, 0, &m, sizeof(m));
                    kout << "\n############## broadcasting my position\n";
                    kout << "## " << m.id << "(" << m.x << "," << m.y << ")\n";
            }
            break;
        }
        case 1 :
        {
            cout << "Creation of an anchor node 'B' in (0,120)\n";
            node = new RSSI(new Anchor('B',0,120, hecops, nic));
            Msg m(0,120,1);
            while(true){
                    node->nic->send(NIC::BROADCAST, 0, &m, sizeof(m));
                    kout << "\n############## broadcasting my position\n";
                    kout << "## " << m.id << "(" << m.x << "," << m.y << ")\n";
            }
            break;
        }
        case 2 :
        {
            cout << "Creation of an anchor node 'C' in (90,0)\n";
            node = new RSSI(new Anchor('C',90,0, hecops, nic));
            Msg m(90,0,2);
            while(true){
                    node->nic->send(NIC::BROADCAST, 0, &m, sizeof(m));
                    kout << "\n############## broadcasting my position\n";
                    kout << "## " << m.id << "(" << m.x << "," << m.y << ")\n";
            }
            break;
        }
        default :
            while (true) {
                cout << "Creation of mobile node 'a'";
                node = new RSSI(new Mobile('a',hecops, nic));
                node->location(node);
            }
            break;
        }
        */
}

