#! /bin/bash


echo "Script name: 	${0}"
echo "Aantal params: 	${#}"
echo "Arg 1: 		${1}"
echo "param 3: 	${3}"
echo "param 10: 	${10}"
if [ "${#}" -gt 3 ]; then
	shift
	shift
	shift
	
fi
echo "Aantal params     ${#}"
echo "Remaining: 	${@}"



