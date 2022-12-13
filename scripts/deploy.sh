REPOSITORY=/home/ec2-user/app
JAR_NAME=hibuddy.jar

cd $REPOSITORY

CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> Nothing to end."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH deploy"

nohup java -jar $REPOSITORY/build/libs/$JAR_NAME 2>&1 &
