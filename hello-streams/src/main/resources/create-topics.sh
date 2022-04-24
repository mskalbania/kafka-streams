docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9093 --topic device-login --partitions 1 --replication-factor 1"
docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --describe --bootstrap-server localhost:9093 --topic device-login"

docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9093 --topic authentication-result --partitions 1 --replication-factor 1"
docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --describe --bootstrap-server localhost:9093 --topic authentication-result"

docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9093 --topic notify --partitions 1 --replication-factor 1"
docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --describe --bootstrap-server localhost:9093 --topic notify"

#/opt/bitnami/kafka/bin/kafka-console-producer.sh --topic device-login --bootstrap-server localhost:9093 --property parse.key=true --property key.separator=:
#/opt/bitnami/kafka/bin/kafka-console-producer.sh --topic authentication-result --bootstrap-server localhost:9093 --property parse.key=true --property key.separator=: