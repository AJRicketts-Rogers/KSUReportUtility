#!/bin/ksh
script="/app/osm/shared/deployment/ksu_report_utility/ksu_report/utility-create-records.sh"
PIDFILE="/app/osm/shared/deployment/ksu_report_utility/ksu_report/utility-create-records.pid"
srvname=`hostname`
if [ -f "${PIDFILE}" ]; then
         echo $script | mailx -s "CRON: $srvname - Previous Intance Already Running" itsngppsupportoncall@rci.rogers.com
         exit 1
else
        touch "${PIDFILE}"
cd /app/osm/shared/deployment/ksu_report_utility/ksu_report/
/app/software/jdk1.8.0_202/bin/java -Xms1g -Xmx2g --Dspring.config.location=file:/app/osm/shared/deployment/ksu_report_utilionruntime.properties -jar KSUReportUtility-1.jar LOADOSMONLY
rm "${PIDFILE}"
fi