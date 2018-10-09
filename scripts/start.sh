#!/bin/sh

###################################
#环境变量及程序执行参数
#需要根据实际环境以及Java程序名称来修改这些参数
###################################
JAVA_HOME=/root/jdk1.8.0_181
#Java程序所在的目录（classes的上一级目录）
APP_HOME=`pwd`
LOG_DIR_OPT="-DAPP_HOME=\"${APP_HOME}\""

#java虚拟机启动参数
JAVA_OPTS="-XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:~/ipass/logs/gc-`date +%Y-%m-%d`.log -Xms256m -Xmx256m -XX:+UseParallelGC -XX:+UseParallelOldGC ${LOG_DIR_OPT}"

nohup $JAVA_HOME/bin/java $JAVA_OPTS -jar ipass-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &