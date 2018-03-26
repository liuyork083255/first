#!/usr/bin/env bash

start_dir=`pwd`
cd `dirname "$0"`

res=`ps aux|grep java|grep com.sumscope.cdh.webbond.App|grep -v grep|awk '{print $2}'`
if [ -n "$res" ]
     then
                echo "Failed:webbond app is already running!"
else
ENV=dev
if [[ $1 != "" ]]; then
    ENV=$1
fi
JAVA_OPT="-server -d64 -Xms2G -Xmx4G -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:./gc.log"
SYS_OPT=""
nohup java -Duniquename=com.sumscope.cdh.webbond.App $JAVA_OPT $SYS_OPT -Dspring.config.name=application.properties -cp ../conf:../lib/* com.sumscope.cdh.webbond.App &> nohup.out &

curr_dir=`pwd`
fails=0
while [ $fails -le 3 ]; do
    for pid in `pgrep java`; do
        one_dir=`readlink -e /proc/$pid/cwd`
        if [ "$one_dir" != "" ] && [ "$one_dir" == "$curr_dir" ]; then
            echo $pid':' $one_dir
            exit 0
        fi
    done
    sleep 1
    fails=$(($fails + 1))
done
echo 'start error...'
tail -n 15 nohup.out

cd $start_dir
fi