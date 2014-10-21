/*
 * location_algorithm.cc
 *
 *  Created on: 25 juin 2013
 *      Author: thibault
 *
 *  This file implements the strategies used for the geographic location.
 *
 */

#include <location_algorithm.h>

//=============================================================================
// common
//=============================================================================

extern "C" { void _exit(int s); }

template <class T>
void calculation(T x[], T y[], T d[], int n, Coordinates *result){
    T xmax, xmin, ymin, ymax, xi, xf, yi, yf;

    kout << "!!! In calculate \n";
    for (int i=0; i<n; i++) {
        kout << i<<":(" << x[i] << ", " << y[i] << ") / " << d[i] << endl ;
    }

    xf = xmax = x[0]+d[0];
    xi = xmin = x[0]-d[0];
    yf = ymax = y[0]+d[0];
    yi = ymin = y[0]-d[0];

    for(int i=1; i<n; i++){
        T   xp = x[i]+d[i], yp = y[i]+d[i],
                xn = x[i]-d[i], yn = y[i]-d[i];

        // �REA M�XIMA
        xmin = Math::min(xmin, xn);   xmax = Math::max(xmax, xp);
        ymin = Math::min(ymin, yn);   ymax = Math::max(ymax, yp);

        // INTERSEC�AO
        xi = Math::max(xi, xn);       xf = Math::min(xf, xp);
        yi = Math::max(yi, yn);       yf = Math::min(yf, yp);

        if(xi >= xf) { xi = xmin; xf = xmax; }
        if(yi >= yf) { yi = ymin; yf = ymax; }
    }

    int N = 2000, w=0;
    T xe, ye, nx, ny;
    T emin = 0xffff, e, lx = xf-xi, ly = yf-yi, dx, dy;
    kout << "lx/ly="<<lx<<"/"<<ly<<endl;
#ifndef _EPOS_
    if(lx == 0 || ly == 0){
        kout << "Division by zero\n";  _exit(1);
    }
#endif
    nx = Math::isqrt(N*lx/ly);
    ny = Math::isqrt(N*ly/lx);
#ifndef _EPOS_
    if(nx == 0 || ny == 0){
        kout << "Division by zero\n"; _exit(2);
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
        kout << "xe=" << xe << endl;
        kout << "ye=" << ye << endl;
    }

    kout << "\n I calculated " << resx << "," << resy << endl;
    result->x = resx;
    result->y = resy;
    kout << "\n I saved " << result->x << "," << result->y << endl;
}

//only with 3 reference nodes
template <class T>
void trilateration(T x[], T y[], double d[], int n, Coordinates *result){
    if (n<3) {
        kout << "Not enough references for trilateration" ;
        return;
    }
    double d0=d[0], d1=d[1], d2=d[2];

    kout << "Let the trilateration begin " << n << endl;
    kout << 0 <<":(" << x[0] << ", " << y[0] << ") / " << (float)d0 << endl ;
    kout << 1 <<":(" << x[1] << ", " << y[1] << ") / " << (float)d1 << endl ;
    kout << 2 <<":(" << x[2] << ", " << y[2] << ") / " << (float)d2 << endl ;

    double A = x[0]*x[0] + y[0]*y[0] - d0*d0;
    double B = x[1]*x[1] + y[1]*y[1] - d1*d1;
    double C = x[2]*x[2] + y[2]*y[2] - d2*d2;
    double X21=x[2]-x[1], X02=x[0]-x[2], X10=x[1]-x[0];
    double Y21=y[2]-y[1], Y02=y[0]-y[2], Y10=y[1]-y[0];
    double num, den;
    num = A*Y21+B*Y02+C*Y10;
    den = 2*(x[0]*Y21 + x[1]*Y02 + x[2]*Y10) ;
    result->x=num/den;
    num = A*X21+B*X02+C*X10;
    den = 2*(y[0]*X21 + y[1]*X02 + y[2]*X10) ;
    result->y=num/den;
}

//=============================================================================
// location algorithm
//=============================================================================

Location_Algorithm::Location_Algorithm()
{
    // TODO Auto-generated constructor stub

}

Location_Algorithm::~Location_Algorithm()
{
    // TODO Auto-generated destructor stub
}

//=============================================================================
// HECOPS
//=============================================================================


void HECOPS::execute(Node *n)
{
    kout << "execute...\n";
    if (!(is_anchor(n->id))) { // is not an anchor (more frequent)
        kout << "calling hecops mobile strategy execution..\n";
        mobile(n);
    } else { // is an anchor
        kout << "calling hecops anchor strategy execution...\n";
        anchor(n);
    }
}

void HECOPS::anchor(Node *node)
{
    /* HECOPS */
    kout << "executing anchor strategy...!\n";
    kout << "node " << node->id << '(' << node->x <<',' << node->y << ")\n";
    // for RSSI Hecops, we need RSSI
    RSSI * nodebis = static_cast<RSSI*>(node);

    Message <RSSI> m(EVERYBODY, nodebis);
    NIC::Protocol prot;
    NIC::Address src;

    while(true){
        for (int i=0; i<5; i++) {
            nodebis->nic->send(NIC::BROADCAST, 0, &m, sizeof(m));
            kout << "############## broadcasting my position\n";
        }
        m.display();
        // Anchors have to broadcast their positions
        Message <RSSI> msg;
        //src = nodebis->id;
        // TODO replace this with interruption
        while(!(nodebis->nic->receive(&src, &prot, &msg, sizeof(msg)) > 0))
            kout << "waiting for a message before broadcasting again\n";
        kout << "############## received \n";
        /*
        msg.display();
        if(is_anchor(msg.src)){
            // If it receives another anchor's position, it will take note
            // of RSSI in a message and broadcast it
            // (for calibration purposes)
            msg.rssi = nodebis->getRSSI();
            Message <RSSI> mg(msg.n.id, EVERYBODY, nodebis);
            nodebis->nic->send(NIC::BROADCAST,0,&mg,sizeof(mg));
            kout << "############## broadcasting other anchor's RSSI \n";
            mg.display();
        }
        */
        Alarm::delay(1000000);
    }
}

/*
long _x[3], _y[3], _rssi[3];
bool _gotcha[3]={false,false,false};
 */

void HECOPS::mobile(Node *node)
{
    /* CALCULATION TESTS
       struct Msg {
           int x;
           int y;
           int id;
       } ;

       NIC::Protocol prot;
       NIC::Address src;
       RSSI * nodebis = static_cast<RSSI*>(node);
       Coordinates* result=new Coordinates();
       Msg msg;

       while(!(nodebis->nic->receive(&src, &prot, &msg, sizeof(msg)) > 0))
           kout << "failed\n";
       kout << "+++\n";
       kout << "Received from" << msg.id << "(" << msg.x << "," << msg.y << ")\n";
       _rssi[msg.id] = nodebis->getRSSI();
       kout << "RSSI = " << _rssi[msg.id] << endl;
       _x[msg.id] = msg.x;
       _y[msg.id] = msg.y;
       _gotcha[msg.id]=true;
       kout << "summary of what has been stored" << endl;
       for (int i=0; i<3; i++) {
           kout << i<<":(" << _x[i] << ", " << _y[i] << ") / " << _rssi[i] << endl ;
       }
       kout << "+++\n";

       if (_gotcha[0] && _gotcha[1] && _gotcha[2]) {
           kout << "I have all I need to calculate \n";
           for (int i=0; i<3; i++) {
               kout << i<<":(" << _x[i] << ", " << _y[i] << ") / " << _rssi[i] << endl ;
           }
           calculate(_x, _y, _rssi, 3, result);
           kout << "\n ____ RESULT FROM CALCULATION ____\n";
           kout << "|    (" << result->x << "," << result->y << ")\n";
           kout << "__________________________________\n";
       } else {
           kout << "Not enough nodes to calculate my position. ";
           kout << "I still need ";
           if (!_gotcha[0]) kout << "0 ";
           if (!_gotcha[1]) kout << "1 ";
           if (!_gotcha[2]) kout << "2 ";
           kout << endl;
       }
     */

    /* HECOPS */
    Message<RSSI> msg;
    // for RSSI Hecops, we need RSSI
    RSSI * nodebis = static_cast<RSSI*>(node);
    HECOPS * pnodebisStrategy = static_cast<HECOPS*> (nodebis->strategy);
    RSSI me(node);
    long x=-1, y=-1;
    int confidence;
    const long rise = 100; long factor=rise;
    long soma=0; int i=0;
    NIC::Protocol msg_prot;
    NIC::Address msg_src;
    kout << "\nbefore the mobile loop\n";

    while(true){
        kout << ".......................................\n ";
        kout << "Right now, I think that I'm here \n";
        kout << nodebis->id << "(" << nodebis->x << "," << nodebis->y << ") / "
                << pnodebisStrategy->confidence << "% sure\n";
        kout << ".......................................\n";
        nl.display();
        while (!(node->nic->receive(&msg_src, &msg_prot, &msg, sizeof(msg))>0 &&
                msg.dst == EVERYBODY && msg.src != nodebis->id))
            kout << "waiting for a message \n";
        kout << "############## received \n";
        msg.display();
        if(msg.src == msg.n.id){
            kout << "A node sent me a message about him\n";
            /// If message information is about the sender,
            /// it's added to the list of nodes with known coordinates
            //nodebis->new_rssi_reading(nodebis->getRSSI());
            SimpleNode *simpleNode = new SimpleNode(msg.n.id, msg.n.x, msg.n.y);
            simpleNode->new_rssi_reading(nodebis->getRSSI());
            //nodebis->rssi=simpleNode->rssi;
            if (nl.insert(*simpleNode)){
                nl.get(msg.n.id)->distance=nodebis->rssi_distance(simpleNode->rssi);
                nl.display();
                int n = Math::min(3,nl.get_size());
                //int n = nl.get_size();
                SimpleNode *nodetmp = nl.first;
                if(n == 1) {
                    /// When there is just one node, an initial
                    /// - and possibly quite wrong - estimation is set
                    x = nodetmp->x + simpleNode->rssi * factor / rise;
                    y = nodetmp->y + simpleNode->rssi * factor / rise;
                    confidence = nl.first->confidence * 3 / 4;
                } else {
                    /// If more than one node is known,
                    /// the estimated coordinates will be calculated
                    long _x[n], _y[n], _rssi[n];
                    double _dist[n];
                    confidence = 0;
                    for(int i=0; i<n; i++){
                        _x[i] = nodetmp->x;
                        _y[i] = nodetmp->y;
                        long c; int ctri;
                        if(nodetmp->dev == -1){
                            c = factor;
                            ctri = 0;
                        }else{
                            c = nodetmp->dev;
                            if(nodetmp->id_tri) ctri = nl.get(nodetmp->id_tri)->confidence;
                            if(ctri < 80) ctri = 0;
                        }
                        kout << "nodetmp->rssi= " << nodetmp->rssi << endl;
                        kout << "c= " << c << endl;
                        kout << "rise= " << rise << endl;
                        _rssi[i] = nodetmp->rssi*c/rise;
                        _dist[i] = nodetmp->distance;
                        //pb here when deviation: approximated to 0 => division by 0 in calculate
                        confidence += (nodetmp->confidence * 3 + ctri) / 4;
                        nodetmp = nodetmp->next;
                    }
                    Coordinates *res = new Coordinates();
                    trilateration(_x,_y,_dist,n,res);
                    //calculate(_x,_y,_rssi,n,res);
                    x=res->x;
                    y=res->y;
                    delete res;
                }
                confidence = confidence * 4 / 15;
                nodebis->x = x;
                nodebis->y = y;
                pnodebisStrategy->confidence = confidence;
                msg.src = nodebis->id;
                msg.dst = EVERYBODY;
                msg.n.x = nodebis->x;
                msg.n.y = nodebis->y;
                msg.n.id = nodebis->id;
                nodebis->nic->send(NIC::BROADCAST,0,&msg,sizeof(msg));
                kout << "############## sending my calculated position \n";
                msg.display();
            }
            delete simpleNode;
        }else{
            /** do nothing for now, I do not want the deviation */
            kout << "The info sent is not about the sender\n";
            /// If message information is not about the sender,
            /// triangulation will be verified
            SimpleNode *no = nl.get(msg.src);
            if(no){
                /** attempt to calculate from rssi
			    long L = distance(no->x, no->y, msg.n.x, msg.n.y);
				kout << "-- Distance between the 2 anchors = " << L << endl;
				if(L / 2 > distance(msg.n.x, msg.n.y, x, y) &&
				        no->confidence > 80 &&
				        nl.get(msg.n.id)->confidence > 80){
                    int A=-62; // 10log(Power transmitted), experimental
                    int n=-37; // experimental
                    long d = Math::pow(10, -(A+no->rssi)/(10*n));
                    // pb with pow, approximation wrong the result
                    no->dev=L/d;
                    no->id_tri=msg.n.id;
                }
                 */
                /* same, v2 */
                long L = Math::distance(no->x,no->y,msg.n.x,msg.n.y);
                if (L/2 > Math::distance(msg.n.x,msg.n.y,x,y) &&
                        no->confidence > 80 &&
                        nl.get(msg.n.id)->confidence > 80) {
                    long d = nodebis->rssi_distance(msg.rssi);
                    if (d==0) {
                        kout << "Something went wrong in the calculation of the distance";
                    } else {
                        no->dev=L/d;
                        no->id_tri=msg.n.id;
                    }
                } else {
                    kout << "This node is too far from me to correct deviation\n";
                }
                /*
                //msg.rssi = nodebis->getRSSI();
                long dist = Math::distance(no->x, no->y, msg.n.x, msg.n.y);
                kout << "-- Distance between the 2 anchors = " << dist << endl;
                factor = dist*rise/msg.rssi;
                soma += factor; i++;
                factor = soma/i;
                kout << "factor=" << factor << endl;
                kout << "-- Distance between the anchor and me = " <<
                        Math::distance(msg.n.x, msg.n.y, x, y) << endl;
                if(dist / 2 > Math::distance(msg.n.x, msg.n.y, x, y)){
                    /// if tri(A,B), deviation will be corrected
                    no->dev = factor;
                    no->id_tri = msg.n.id;
                } else {
                    kout << "This node is too far from me to correct deviation\n";
                }
                 */
            } else { kout << "I don't have this node in my list, I cannot correct deviation \n";}
        }
    }
}

template <class T>
void HECOPS::calculate(T x[], T y[], T d[], int n, Coordinates *result){
    calculation(x,y,d,n,result);
}

//=============================================================================
// EHECOPS
//=============================================================================

void EHECOPS::execute(Node *n)
{
    kout << "execute...\n";
    if (!(is_anchor(n->id))) { // is not an anchor (more frequent)
        kout << "calling ehecops mobile strategy execution..\n";
        mobile(n);
    } else { // is an anchor
        kout << "calling ehecops anchor strategy execution...\n";
        anchor(n);
    }
}

void EHECOPS::anchor(Node *node) {
    // find the pointer to RSSI and Ultrasound in the structure
    // needs to be cast to smthing w/ m_wrappee
    Decorator *tmp = static_cast<Decorator*>(node);
    int done=0, j=0;;
    RSSI* nodeRssi;
    Ultrasound *nodeUltrasound;

    while(tmp->getType() != enumerated::Anchor && tmp->getType() != enumerated::Mobile) {
        switch (tmp->getType()) {
        case enumerated::Ultrasound:
            nodeUltrasound = static_cast<Ultrasound*>(tmp);
            done++;
            break;
        case enumerated::RSSI:
            nodeRssi = static_cast<RSSI*>(tmp);
            done++;
            break;
        default:
            break;
        }
        tmp=static_cast<Decorator*>(tmp->getWrappee());
        j++;
    }

    if (done!=j) {
        kout << "\n error: strategy not applicable \n"; _exit(1);
    }

    /* HECOPS */
    kout << "executing anchor strategy...!\n";
    kout << "node " << node->id << '(' << node->x <<',' << node->y << ")\n";
    Coordinates sender;

    Message <Node> m(EVERYBODY, node);
    NIC::Protocol prot;
    NIC::Address src;

    while(true){
        node->nic->send(NIC::BROADCAST, 0, &m, sizeof(m));
        kout << "############## broadcasting my position\n";
        m.display();
        // Anchors have to broadcast their positions
        Message <RSSI> msg;
        //src = nodeRssi->id;
        // TODO replace this with interruption
        while(!(node->nic->receive(&src, &prot, &msg, sizeof(msg)) > 0))
            kout << "waiting for a message before broadcasting again\n";
        kout << "############## received \n";
        msg.display();
        if(is_anchor(msg.src)){
            // If it receives another anchor's position, it will take note
            // of RSSI in a message and broadcast it
            // (for calibration purposes)
            msg.rssi = nodeRssi->getRSSI();
            Message <RSSI> mg(msg.n.id, EVERYBODY, nodeRssi);
            nodeRssi->nic->send(NIC::BROADCAST,0,&mg,sizeof(mg));
            kout << "############## broadcasting other anchor's RSSI \n";
            mg.display();
        } else {
            // If I receive a message from a mobile node, I check if I can help
            // him to determine his position by sending him the distance
            // between us
            sender.x=msg.n.x;
            sender.y=msg.n.y;
            Message <Ultrasound> mg(msg.src, msg.src, nodeUltrasound);
            mg.distance=nodeUltrasound->distance(sender);
            if (mg.distance > 0) {
                // send a message concerning the sender
                while (true){
                    nodeUltrasound->nic->send(NIC::BROADCAST,0,&mg,sizeof(mg));
                    kout << "############## sending this back to " << msg.src << endl;
                    mg.display();
                }
            } else {
                if (mg.distance == -2) {
                    // TODO tell the node that I didn't find him with ultrasound
                    // although I should have (his position is not accurate)
                }
            }
        }
        Alarm::delay(100000);
    }
}

void EHECOPS::mobile(Node *node)
{
    /* EHECOPS */
    //Message<RSSI> msg, *m;
    Message<Ultrasound> msg;
    // for RSSI Hecops, we need RSSI
    RSSI * nodebis = static_cast<RSSI*>(node);
    EHECOPS * pnodebisStrategy = static_cast<EHECOPS*> (nodebis->strategy);
    RSSI me(node);
    long x=-1, y=-1;
    int confidence;
    const long rise = 100; long factor=rise;
    long soma=0; int i=0;
    NIC::Protocol msg_prot;
    NIC::Address msg_src;
    kout << "\nbefore the mobile loop\n";

    while(true){
        kout << ".......................................\n ";
        kout << "Right now, I think that I'm here \n";
        kout << nodebis->id << "(" << nodebis->x << "," << nodebis->y << ") / "
                << pnodebisStrategy->confidence << "% sure\n";
        kout << ".......................................\n";
        nl.display();

        while (!(node->nic->receive(&msg_src, &msg_prot, &msg, sizeof(msg))>0 &&
                (msg.dst == EVERYBODY || msg.dst == node->id)))
            // a node receives the info it sends?                && msg.src != nodebis->id))
            kout << "waiting for a message... \n";
        kout << "############## received \n";
        msg.display();
        if (msg.src==msg.n.id) {
            // TODO info about the sender: add to the list of nodes w/ known coordinates
        } else {
            if (msg.src==node->id) {
                kout << "Information concerning the sender and me\n";
                // info about the sender and me: check what I can do w/ that
                int dist = Math::distance(node->x, node->y, msg.n.x, msg.n.y);
                kout << "Distance between us = " << dist << endl;
                // if a distance between an anchor and me has been measured and is
                // in contradiction with my current value, I reevaluate my position
                if (msg.distance > 0 && (Math::abs((float)msg.distance-(float)dist)/(float)msg.distance) > 0.05) {
                    kout << "The other node has measured a distance between us, and I am far from it\n" ;
                    // calibrate
                    float ratio = (float)msg.distance/(float)dist;
                    node->x=ratio*node->x;
                    node->y=ratio*node->y;
                    // TODO increase confidence in my position
                }
            } else {
                // TODO info about another node: check what I can do /w that
            }
        }
    }
}
