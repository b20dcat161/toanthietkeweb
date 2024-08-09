FROM tomcat
COPY dist/toanthietkeweb.com.war /usr/local/tomcat/webapps/ROOT.war
USER root
RUN apt-get update && \
    apt-get install -y ffmpeg && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*
