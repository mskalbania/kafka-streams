docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9093 --topic scores --partitions 3 --replication-factor 1"
docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9093 --topic players --partitions 3 --replication-factor 1"
docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9093 --topic products --partitions 1 --replication-factor 1"
docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9093 --topic high-scores --partitions 1 --replication-factor 1"

docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --describe --bootstrap-server localhost:9093 --topic scores"
docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --describe --bootstrap-server localhost:9093 --topic players"
docker exec kafka1 sh -c "exec /opt/bitnami/kafka/bin/kafka-topics.sh --describe --bootstrap-server localhost:9093 --topic products"