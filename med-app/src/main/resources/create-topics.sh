docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9093 --topic heartbeats --partitions 3 --replication-factor 1 --config message.timestamp.type=CreateTime"
docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9093 --topic temperature --partitions 3 --replication-factor 1 --config message.timestamp.type=CreateTime"

docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --describe --bootstrap-server localhost:9093 --topic heartbeats"
docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --describe --bootstrap-server localhost:9093 --topic temperature"