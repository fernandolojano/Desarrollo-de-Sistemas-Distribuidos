/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#include "calculadora.h"
#include <stdio.h>
#include <stdlib.h>
#include <rpc/pmap_clnt.h>
#include <string.h>
#include <memory.h>
#include <sys/socket.h>
#include <netinet/in.h>

#ifndef SIG_PF
#define SIG_PF void(*)(int)
#endif

static double *
_sumar_1 (datos  *argp, struct svc_req *rqstp)
{
	return (sumar_1_svc(*argp, rqstp));
}

static double *
_restar_1 (datos  *argp, struct svc_req *rqstp)
{
	return (restar_1_svc(*argp, rqstp));
}

static double *
_multiplicar_1 (datos  *argp, struct svc_req *rqstp)
{
	return (multiplicar_1_svc(*argp, rqstp));
}

static double *
_dividir_1 (datos  *argp, struct svc_req *rqstp)
{
	return (dividir_1_svc(*argp, rqstp));
}

static double *
_potencia_1 (datos  *argp, struct svc_req *rqstp)
{
	return (potencia_1_svc(*argp, rqstp));
}

static datos *
_sumarvector_1 (datos  *argp, struct svc_req *rqstp)
{
	return (sumarvector_1_svc(*argp, rqstp));
}

static datos *
_restarvector_1 (datos  *argp, struct svc_req *rqstp)
{
	return (restarvector_1_svc(*argp, rqstp));
}

static double *
_multiplicarvector_1 (datos  *argp, struct svc_req *rqstp)
{
	return (multiplicarvector_1_svc(*argp, rqstp));
}

static void
calculadora_1(struct svc_req *rqstp, register SVCXPRT *transp)
{
	union {
		datos sumar_1_arg;
		datos restar_1_arg;
		datos multiplicar_1_arg;
		datos dividir_1_arg;
		datos potencia_1_arg;
		datos sumarvector_1_arg;
		datos restarvector_1_arg;
		datos multiplicarvector_1_arg;
	} argument;
	char *result;
	xdrproc_t _xdr_argument, _xdr_result;
	char *(*local)(char *, struct svc_req *);

	switch (rqstp->rq_proc) {
	case NULLPROC:
		(void) svc_sendreply (transp, (xdrproc_t) xdr_void, (char *)NULL);
		return;

	case sumar:
		_xdr_argument = (xdrproc_t) xdr_datos;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) _sumar_1;
		break;

	case restar:
		_xdr_argument = (xdrproc_t) xdr_datos;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) _restar_1;
		break;

	case multiplicar:
		_xdr_argument = (xdrproc_t) xdr_datos;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) _multiplicar_1;
		break;

	case dividir:
		_xdr_argument = (xdrproc_t) xdr_datos;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) _dividir_1;
		break;

	case potencia:
		_xdr_argument = (xdrproc_t) xdr_datos;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) _potencia_1;
		break;

	case sumarVector:
		_xdr_argument = (xdrproc_t) xdr_datos;
		_xdr_result = (xdrproc_t) xdr_datos;
		local = (char *(*)(char *, struct svc_req *)) _sumarvector_1;
		break;

	case restarVector:
		_xdr_argument = (xdrproc_t) xdr_datos;
		_xdr_result = (xdrproc_t) xdr_datos;
		local = (char *(*)(char *, struct svc_req *)) _restarvector_1;
		break;

	case multiplicarVector:
		_xdr_argument = (xdrproc_t) xdr_datos;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) _multiplicarvector_1;
		break;

	default:
		svcerr_noproc (transp);
		return;
	}
	memset ((char *)&argument, 0, sizeof (argument));
	if (!svc_getargs (transp, (xdrproc_t) _xdr_argument, (caddr_t) &argument)) {
		svcerr_decode (transp);
		return;
	}
	result = (*local)((char *)&argument, rqstp);
	if (result != NULL && !svc_sendreply(transp, (xdrproc_t) _xdr_result, result)) {
		svcerr_systemerr (transp);
	}
	if (!svc_freeargs (transp, (xdrproc_t) _xdr_argument, (caddr_t) &argument)) {
		fprintf (stderr, "%s", "unable to free arguments");
		exit (1);
	}
	return;
}

int
main (int argc, char **argv)
{
	register SVCXPRT *transp;

	pmap_unset (CALCULADORA, CALCULADORAV0);

	transp = svcudp_create(RPC_ANYSOCK);
	if (transp == NULL) {
		fprintf (stderr, "%s", "cannot create udp service.");
		exit(1);
	}
	if (!svc_register(transp, CALCULADORA, CALCULADORAV0, calculadora_1, IPPROTO_UDP)) {
		fprintf (stderr, "%s", "unable to register (CALCULADORA, CALCULADORAV0, udp).");
		exit(1);
	}

	transp = svctcp_create(RPC_ANYSOCK, 0, 0);
	if (transp == NULL) {
		fprintf (stderr, "%s", "cannot create tcp service.");
		exit(1);
	}
	if (!svc_register(transp, CALCULADORA, CALCULADORAV0, calculadora_1, IPPROTO_TCP)) {
		fprintf (stderr, "%s", "unable to register (CALCULADORA, CALCULADORAV0, tcp).");
		exit(1);
	}

	svc_run ();
	fprintf (stderr, "%s", "svc_run returned");
	exit (1);
	/* NOTREACHED */
}
