FROM mysql:8
ENV MYSQL_ROOT_PASSWORD password
ENV MYSQL_DATABASE example_db
ENV MYSQL_USER user
ENV MYSQL_PASSWORD password
# File from the previous step
COPY setup.sql /docker-entrypoint-initdb.d
EXPOSE 3306