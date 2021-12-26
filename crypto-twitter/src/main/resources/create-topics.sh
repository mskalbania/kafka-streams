docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9093 --topic tweets --partitions 2 --replication-factor 1"
docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9093 --topic analyzed --partitions 2 --replication-factor 1"
docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --describe --bootstrap-server localhost:9093 --topic tweets"
docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --describe --bootstrap-server localhost:9093 --topic analyzed"

