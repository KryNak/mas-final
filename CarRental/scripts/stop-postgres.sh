docker stop car-rental-db > /dev/null && echo "car-rental-db stopped"
docker rm car-rental-db > /dev/null && echo "car-rental-db removed"

docker stop car-rental-db-admin > /dev/null && echo "car-rental-db-admin stopped"
docker rm car-rental-db-admin > /dev/null && echo "car-rental-db-admin removed"
