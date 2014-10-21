/*
 * node.cc
 *
 *  Created on: 25 juin 2013
 *      Author: thibault
 */

#include <node.h>
#include <location_algorithm.h>

/******************************************************************************
 * NODE
 *****************************************************************************/

Node::Node(){}

Node::Node(char id, long x, long y): id(id),x(x),y(y) {
}

Node::Node(char id, long x, long y, Location_Algorithm* strategy, NIC* nic):
               id(id),x(x),y(y),strategy(strategy),nic(nic) {}

Node::Node(char id, Location_Algorithm* strategy, NIC* nic):
        id(id),strategy(strategy),nic(nic) {}


bool Node::equals(Node *n)
{
    return (id == n->id && x == n->x && y == n->y);
}

/******************************************************************************
 * ANCHOR
 *****************************************************************************/

Anchor::Anchor() {}

Anchor::Anchor(char id, long x, long y): Node(id, x, y)
{
    dev = -1;
    id_tri = 0;
}

Anchor::Anchor(char id, long x, long y, Location_Algorithm* strategy, NIC* nic):
        Node(id, x, y, strategy, nic)
{
    dev = -1;
    id_tri = 0;
}

Anchor::Anchor(char id, Location_Algorithm* strategy, NIC* nic):
        Node(id, strategy, nic) {}

Anchor::Anchor(Anchor &n)
{
    this->strategy = n.strategy;
    *this = n;
}

Anchor::Anchor(Node *n)//:id(n->id),x(n->x),y(n->y),strategy(n->strategy),nic(n->nic)
{
    // we create a new instance but we copy the values
    //Anchor(n->id,n->x,n->y,n->strategy,n->nic);
    id=n->id;
    x=n->x;
    y=n->y;
    strategy=n->strategy;
    nic=n->nic;
}

Anchor::~Anchor()
{
    delete this;
}

void Anchor::location(Node *wrapper)
{
    kout << "...Anchor...";
    kout << "\nAbout to execute the strategy...\n";
    this->strategy->execute(wrapper);
}

enumerated::Type Anchor::getType()
{
    return enumerated::Anchor;
}

/******************************************************************************
 * MOBILE
 *****************************************************************************/

Mobile::Mobile(){}

Mobile::Mobile(char id, long x, long y): Node(id, x, y)
{
    kout << "+++ Mobile constructor +++";
    dev = -1;
    id_tri = 0;
}

Mobile::Mobile(char id, long x, long y, Location_Algorithm* strategy, NIC* nic):
        Node(id, x, y, strategy, nic)
{
    dev = -1;
    id_tri = 0;
}

Mobile::Mobile(char id, Location_Algorithm* strategy, NIC* nic):
        Node(id, strategy,nic) {}

Mobile::Mobile(Mobile &n)
{
    this->strategy = n.strategy;
    *this = n;
}

Mobile::Mobile(Node *n)
{
    // we create a new instance but we copy the values
    //Anchor(n->id,n->x,n->y,n->strategy,n->nic);
    id=n->id;
    x=n->x;
    y=n->y;
    strategy=n->strategy;
    nic=n->nic;
}

Mobile::~Mobile()
{
    kout << "Mobile dtor" << '\n';
}


void Mobile::location(Node *wrapper)
{
    kout << "...Mobile...";
    kout << "\nAbout to execute the strategy...\n";
    this->strategy->execute(wrapper);
}

enumerated::Type Mobile::getType()
{
    return enumerated::Mobile;
}

/******************************************************************************
 * DECORATOR
 *****************************************************************************/

Decorator::Decorator( Node* inner ): Node(inner->id,inner->x,inner->y,inner->strategy,inner->nic) {
    m_wrappee = inner;
}

Decorator::~Decorator()
{
    delete m_wrappee;
}

void Decorator::location(Node *wrapper)
{
    m_wrappee->location(wrapper);
}

/******************************************************************************
 * RSSI
 *****************************************************************************/

RSSI::RSSI( Node* core ) : Decorator(core)
{
    this->hasRSSI=true;
    d0=100;
    Pd0=-57;
    path_loss=2.83;
}

RSSI::RSSI( Node* core, int d0, int Pd0, float path_loss ) :
        Decorator(core), d0(d0), Pd0(Pd0), path_loss(path_loss)
{
    this->hasRSSI=true;
}

RSSI::~RSSI()
{
    this->hasRSSI=false;
}

void RSSI::location(Node *wrapper)
{
    kout << "...RSSI...";
    if (wrapper == this) Decorator::location(this);
    else Decorator::location(wrapper);
}
/*
void RSSI::new_rssi_reading(int reading){
    if (n>3) {
        // we restrict the average to 3 measurements in case the node moves
        rssi_sum -= rssi;
        rssi_sum += reading;
        rssi = rssi_sum / n;
    } else {
        rssi_sum += reading;
        n++;
        rssi = rssi_sum / n;
    }
}
*/
enumerated::Type RSSI::getType()
{
    return enumerated::RSSI;
}

int RSSI::getRSSI() { return _radio.rssi(); }


double RSSI::rssi_distance(long rssi) {
    double e=((double)Pd0-(double)rssi)/10*(double)path_loss;
    double res = d0 * Math::exp(e);
    if (res > 0) return res;
    else return 0;
}
/******************************************************************************
 * Ultrasound
 *****************************************************************************/

Ultrasound::Ultrasound( Node* core ) : Decorator(core)
{
    this->hasUS=true;
}

Ultrasound::Ultrasound( Node* core, int maxRange, int minRange, int alpha, Coordinates* orientation ):
        Decorator(core), maxRange(maxRange), minRange(minRange), alpha(alpha), orientation(orientation)
{
    setRL();
}


Ultrasound::~Ultrasound()
{
    this->hasUS=false;
}

void Ultrasound::location(Node *wrapper)
{
    kout << "...Ultrasound...";
    if (wrapper == this) Decorator::location(this);
    else Decorator::location(wrapper);
}

/* A utility function to calculate area of triangle formed by (x1, y1),
   (x2, y2) and (x3, y3) */
float Ultrasound::area(int x1, int y1, int x2, int y2, int x3, int y3)
{
   return Math::abs((x1*(y2-y3) + x2*(y3-y1)+ x3*(y1-y2))/2.0);
}

/* A function to check whether point P(x, y) lies inside the triangle formed
   by A(x1, y1), B(x2, y2) and C(x3, y3) */
bool Ultrasound::inRangeTriangle(Coordinates dest) {

    /* Calculate area of triangle ABC */
    float A = area (R.x, R.y, L.x, L.y, x, y);

    /* Calculate area of triangle PBC */
    float A1 = area (dest.x, dest.y, L.x, L.y, x, y);

    /* Calculate area of triangle PAC */
    float A2 = area (R.x, R.y, dest.x, dest.y, x, y);

    /* Calculate area of triangle PAB */
    float A3 = area (R.x, R.y, L.x, L.y, dest.x, dest.y);

    /* Check if sum of A1, A2 and A3 is same as A, accepting 5% of error
     * (error can be inserted during the calculation of the triangle points
     * and due to the hardware uncertainty)
     * */

    return (Math::abs(A - (A1 + A2 + A3))/A < 0.05);
}

void Ultrasound::rotate(Coordinates src, Coordinates * dst, int phi) {
    double phirad,pi=3.14;
    phirad=phi*(pi/180);
    dst->x=(double)src.x*Math::cosine(phirad)-(double)src.y*Math::sine(phirad);
    dst->y=(double)src.x*Math::sine(phirad)+(double)src.y*Math::cosine(phirad);
}

void Ultrasound::setRL() {
    int x=orientation->x;
    int y=orientation->y;
    // normalization of the vector to have it at the same size as rangeMax
    float a=maxRange / (Math::pow(x,2)+Math::pow(y,2));
    R.x=a*x;
    R.y=a*y;
    // rotate the vector to have the other side of the triangle
    orientation->x=a*x;
    orientation->y=a*y;
    rotate(*orientation,&L,alpha);
}

int Ultrasound::distance(Coordinates node){
    if (!inRangeTriangle(node)) {
        kout << "not in triangle \n";
        return -1;
    } else {
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // ! TODO send signal and measure time (in seconds) !
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        kout << "in triangle \n";
        int time;
        // return -2 if time has expired, means that his estimation is wrong
        // if (time > maxRange*2/240) return -2;
        return 17;//time*340/2;
    }
}

enumerated::Type Ultrasound::getType()
{
    return enumerated::Ultrasound;
}

/******************************************************************************
 * NFC
 *****************************************************************************/

NFC::NFC( Node* core ) : Decorator(core)
{
}

int NFC::getNFC() {
    // return NFC value
    return -1;
}

