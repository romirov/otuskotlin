# Pull the minimal Ubuntu image
FROM ubuntu

# Update and install components
RUN apt-get update \
    && apt-get install -y locales default-jdk default-jre && rm -rf /var/lib/apt/lists/* \
    && localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8

# Set locale
ENV LANG en_US.utf8

# Expose the port for access
EXPOSE 8080/tcp

# Copy jar to work directory
COPY *.jar /opt/

# Changing directory to work directory
WORKDIR /usr/src/

# Run the project
CMD ["java", "-jar", "*.jar"]