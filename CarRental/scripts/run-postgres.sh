docker version > /dev/null || (open /Applications/Docker.app && sleep 15)

docker run --name car-rental-db -e POSTGRES_PASSWORD=qwer1234 -p 5432:5432 -d postgres > /dev/null
docker run --name car-rental-db-admin -e PGADMIN_DEFAULT_EMAIL='krystian.nakielski200397@gmail.com' -e PGADMIN_DEFAULT_PASSWORD=qwer1234 -p 8180:80 -d dpage/pgadmin4 > /dev/null
docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' car-rental-db
