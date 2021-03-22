/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#ifndef _CALCULADORA_H_RPCGEN
#define _CALCULADORA_H_RPCGEN

#include <rpc/rpc.h>


#ifdef __cplusplus
extern "C" {
#endif


struct sumar_1_argument {
	double arg1;
	double arg2;
};
typedef struct sumar_1_argument sumar_1_argument;

struct restar_1_argument {
	double arg1;
	double arg2;
};
typedef struct restar_1_argument restar_1_argument;

struct multiplicar_1_argument {
	double arg1;
	double arg2;
};
typedef struct multiplicar_1_argument multiplicar_1_argument;

struct dividir_1_argument {
	double arg1;
	double arg2;
};
typedef struct dividir_1_argument dividir_1_argument;

#define CALCULADORA 0x20000001
#define CALCULADORAV0 1

#if defined(__STDC__) || defined(__cplusplus)
#define sumar 1
extern  double * sumar_1(double , double , CLIENT *);
extern  double * sumar_1_svc(double , double , struct svc_req *);
#define restar 2
extern  double * restar_1(double , double , CLIENT *);
extern  double * restar_1_svc(double , double , struct svc_req *);
#define multiplicar 3
extern  double * multiplicar_1(double , double , CLIENT *);
extern  double * multiplicar_1_svc(double , double , struct svc_req *);
#define dividir 4
extern  double * dividir_1(double , double , CLIENT *);
extern  double * dividir_1_svc(double , double , struct svc_req *);
extern int calculadora_1_freeresult (SVCXPRT *, xdrproc_t, caddr_t);

#else /* K&R C */
#define sumar 1
extern  double * sumar_1();
extern  double * sumar_1_svc();
#define restar 2
extern  double * restar_1();
extern  double * restar_1_svc();
#define multiplicar 3
extern  double * multiplicar_1();
extern  double * multiplicar_1_svc();
#define dividir 4
extern  double * dividir_1();
extern  double * dividir_1_svc();
extern int calculadora_1_freeresult ();
#endif /* K&R C */

/* the xdr functions */

#if defined(__STDC__) || defined(__cplusplus)
extern  bool_t xdr_sumar_1_argument (XDR *, sumar_1_argument*);
extern  bool_t xdr_restar_1_argument (XDR *, restar_1_argument*);
extern  bool_t xdr_multiplicar_1_argument (XDR *, multiplicar_1_argument*);
extern  bool_t xdr_dividir_1_argument (XDR *, dividir_1_argument*);

#else /* K&R C */
extern bool_t xdr_sumar_1_argument ();
extern bool_t xdr_restar_1_argument ();
extern bool_t xdr_multiplicar_1_argument ();
extern bool_t xdr_dividir_1_argument ();

#endif /* K&R C */

#ifdef __cplusplus
}
#endif

#endif /* !_CALCULADORA_H_RPCGEN */
