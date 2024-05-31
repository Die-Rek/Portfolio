#! /bin/bash
set -o nounset
if [ "${#}" -eq 0 ];then
	echo "Geen Argumenten opgegeven!"
else
	for i in "${@}"; do
		echo "${i}"
	done
fi
