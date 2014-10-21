#ifndef _LOCALIZA_H_
#define _LOCALIZA_H_

#ifdef _EPOS_
#define NULL 0
#endif
// Convention: Node x is ANCHOR if 'A' <= x.id <= 'Z'
//             Node x is MOBILE if 'a' <= x.id <= 'z'

const char BASE      = '#';
const char EVERYBODY = '*';

inline bool is_base(char who){
	return who == 'A';
}

inline bool is_anchor(char who){
	return (who >= 'A' && who <= 'Z');
}

struct Node{ /// Useful node information for localization
	char id;
	long x, y, rssi; unsigned long rssi_sum, n;
	int confidence;
	int dev;
	char id_tri;
	Node *next;

	Node(){}

	Node(char id, long x, long y){
		confidence = is_anchor(id) ? 100 : 0;
		this->x = x;
		this->y = y;
		this->id = id;
		dev = -1;
		id_tri = 0;
		rssi_sum = 0;
		n = 0;
	}

	Node(Node &n){
		*this = n;
	}

	void new_rssi_reading(int reading){
		rssi_sum += reading;
		n++;
		rssi = rssi_sum / n;
	}
	
	bool equals(Node *n){
		return (id == n->id && x == n->x && y == n->y && 
			confidence == n->confidence && rssi == n->rssi);
	}
};

struct NodeList{
	Node *first;
	int size;

	NodeList(){
		first = NULL;
		size = 0;
	}

	bool insert(Node n){
		Node *x;
		for(x = first; x!=NULL; x=x->next){
			if(n.id == x->id){
				if(n.equals(x)) {
					return false;
				} else {
					if(x->rssi != n.rssi)
						x->new_rssi_reading(n.rssi);
					n.x = x->x; n.y = x->y; n.confidence = x->confidence;
					return true;
				}
			}
		}

		Node *nw = new Node(n); 
		if(first == NULL){
			first = nw; nw->next = NULL;
		}else{
			bool done = false; Node *ant = NULL;
			for(x = first; x!=NULL; x=x->next){
				if(nw->confidence > x->confidence){
					nw->next = x;
					if(x == first) first = nw;
					else ant->next = nw;
					done = true;
				}
				ant = x;
			}
			if(!done){ ant->next = nw; nw->next = NULL; }
		}
		size++; //printf("'%c' inserted\n", n.id);
		return true;
	}
	
	void remove(Node *n){
		if(size == 0) return;
		if(first == n){
			first = n->next;
		} else {
			Node *x, *ant = first;
			for(x=first->next; x!=NULL; x=x->next){
				if(x == n){
					ant->next = x->next;
					break;
				}
				ant = x;
			}
		}
		size--;
		delete n;
	}

	Node *get(char id){
		Node *x;
		for(x = first; x!=NULL; x=x->next){
			if(id == x->id) return x;
		}
		return NULL;
	}

	int get_size(){
		return size;
	}
};


struct Message{
	int rssi;
	char src;
	char dst;
	Node n;

	Message(){}

	Message(char src, char dst){
		this->src = src;
		this->dst = dst;
	}

	Message(char src, char dst, Node *n){	
		this->n = *n;
		this->src = src;
		this->dst = dst;
	}

	Message(char dst, Node *n){
		this->n = *n;
		this->src = n->id;
		this->dst = dst;
	}
};

template <class T>
void calculate(T x[], T y[], T d[], int n, Node *result){
	T xmax, xmin, ymin, ymax, xi, xf, yi, yf;

	xf = xmax = x[0]+d[0];
	xi = xmin = x[0]-d[0];
	yf = ymax = y[0]+d[0];
	yi = ymin = y[0]-d[0];

	for(int i=1; i<n; i++){
		T   xp = x[i]+d[i], yp = y[i]+d[i],
		    xn = x[i]-d[i], yn = y[i]-d[i];
		
		// ÁREA MÁXIMA
		xmin = min(xmin, xn);   xmax = max(xmax, xp);
		ymin = min(ymin, yn);   ymax = max(ymax, yp);

		// INTERSECÇAO
		xi = max(xi, xn);       xf = min(xf, xp);
		yi = max(yi, yn);       yf = min(yf, yp);

		if(xi >= xf) { xi = xmin; xf = xmax; }
		if(yi >= yf) { yi = ymin; yf = ymax; }
	}
	//printf("inter (%l,%l) (%l,%l) max (%l,%l) (%l,%l) n0(%l,%l,%l) n1(%l,%l,%l)\n", xi,yi,xf,yf,xmin,ymin,xmax,ymax,x[0],y[0],d[0],x[1],y[1],d[1]);	

	int N = 2000, w=0;
	T xe, ye, nx, ny;
	T emin = 0xffff, e, lx = xf-xi, ly = yf-yi, dx, dy;
#ifndef _EPOS_
	if(lx == 0 || ly == 0){ printf("Putz! Divisao por 0! lx=%l ly=%l\n", lx, ly); _exit(1); }
#endif
	nx = isqrt(N*lx/ly);
	ny = isqrt(N*ly/lx);
#ifndef _EPOS_
	if(nx == 0 || ny == 0){ printf("Putz! Divisao por 0! nx=%l ny=%l\n", nx, ny); _exit(2); }
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
				e += abs(distance(x[k],y[k],xe,ye)-d[k]);
			if(e < emin){
				emin = e;
				resx = xe; resy = ye;
				//printf("| x=%l y=%l e=%l ", result.x, result.y, e);
			}
			w++;
			while((++j*dy)+yi == ye);
		}
		while((++i*dx)+xi == xe);
	}

	result->x = resx;
	result->y = resy;
}

#endif
