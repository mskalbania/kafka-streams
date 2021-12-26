docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9093 --topic users --partitions 2 --replication-factor 1"
docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --describe --bootstrap-server localhost:9093 --topic users"

docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9093 --topic admins --partitions 1 --replication-factor 1"
docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --describe --bootstrap-server localhost:9093 --topic admins"