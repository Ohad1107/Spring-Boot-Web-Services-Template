FROM alpine:3.4

# Java Version and other ENV
ENV JAVA_MAJOR=8 \
    JAVA_MINOR=102 \
    JAVA_HOME=/opt/jdk \
    PATH=${PATH}:/opt/jdk/bin \
    GLIBC_VERSION=2.23-r3 \
    LANG=C.UTF-8 \
    ARTIFACTORY_URL=http://ran-artifactory:8081/artifactory/java-dist

# do all in one step
RUN apk upgrade --update && \
    apk add --update libstdc++ curl ca-certificates bash && \
    for pkg in glibc-${GLIBC_VERSION} glibc-bin-${GLIBC_VERSION} glibc-i18n-${GLIBC_VERSION}; do curl -sSL ${ARTIFACTORY_URL}/glibc/${pkg}.apk -o /tmp/${pkg}.apk; done && \
    apk add --allow-untrusted /tmp/*.apk && \
    rm -v /tmp/*.apk && \
    ( /usr/glibc-compat/bin/localedef --force --inputfile POSIX --charmap UTF-8 C.UTF-8 || true ) && \
    echo "export LANG=C.UTF-8" > /etc/profile.d/locale.sh && \
    /usr/glibc-compat/sbin/ldconfig /lib /usr/glibc-compat/lib && \
    mkdir /opt && \
    curl -SL ${ARTIFACTORY_URL}/jdk-${JAVA_MAJOR}u${JAVA_MINOR}-linux-x64.tar.gz -o /tmp/java.tar.gz && \
    curl -SL ${ARTIFACTORY_URL}/jce_policy-${JAVA_MAJOR}.zip -o /tmp/jce_policy-${JAVA_MAJOR}.zip && \
    gunzip /tmp/java.tar.gz && \
    tar -C /opt -xvf /tmp/java.tar && \
    ln -s /opt/jdk1.${JAVA_MAJOR}.0_${JAVA_MINOR} /opt/jdk && \
    find /opt/jdk/ -maxdepth 1 -mindepth 1 | grep -v jdk | xargs rm -rf && \
    cd /opt/jdk/ && \
    cd /tmp && unzip /tmp/jce_policy-${JAVA_MAJOR}.zip && \
      cp -v /tmp/UnlimitedJCEPolicyJDK8/*.jar /opt/jdk/jre/lib/security/ && \
    sed -i s/#networkaddress.cache.ttl=-1/networkaddress.cache.ttl=30/ $JAVA_HOME/jre/lib/security/java.security && \
    apk del curl glibc-i18n && \
    rm -rf /opt/jdk/plugin \
           /opt/jdk/bin/javaws \
           /opt/jdk/bin/jjs \
           /opt/jdk/bin/keytool \
           /opt/jdk/bin/orbd \
           /opt/jdk/bin/pack200 \
           /opt/jdk/bin/policytool \
           /opt/jdk/bin/rmid \
           /opt/jdk/bin/rmiregistry \
           /opt/jdk/bin/servertool \
           /opt/jdk/bin/tnameserv \
           /opt/jdk/bin/unpack200 \
           /opt/jdk/lib/javaws.jar \
           /opt/jdk/lib/deploy* \
           /opt/jdk/lib/desktop \
           /opt/jdk/lib/*javafx* \
           /opt/jdk/lib/*jfx* \
           /opt/jdk/lib/amd64/libdecora_sse.so \
           /opt/jdk/lib/amd64/libprism_*.so \
           /opt/jdk/lib/amd64/libfxplugins.so \
           /opt/jdk/lib/amd64/libglass.so \
           /opt/jdk/lib/amd64/libgstreamer-lite.so \
           /opt/jdk/lib/amd64/libjavafx*.so \
           /opt/jdk/lib/amd64/libjfx*.so \
           /opt/jdk/lib/ext/jfxrt.jar \
           /opt/jdk/lib/ext/nashorn.jar \
           /opt/jdk/lib/oblique-fonts \
           /opt/jdk/lib/plugin.jar \
           /tmp/* /var/cache/apk/* && \
    echo 'hosts: files mdns4_minimal [NOTFOUND=return] dns mdns4' >> /etc/nsswitch.conf

ADD build/libs/template-0.0.1-SNAPSHOT.jar /opt/ms/template-0.0.1.jar
EXPOSE 8080
CMD ["java", "-jar", "/opt/ms/template-0.0.1.jar"]
