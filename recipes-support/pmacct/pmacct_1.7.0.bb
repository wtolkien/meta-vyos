DESCRIPTION = "Promiscuous mode IP Accounting package"
HOMEPAGE = "http://www.pmacct.net/"
LICENSE = "GPLv2"
DEPENDS = "libpcap libnetfilter-log gettext-native autogen-native \
        autoconf-native libtool-native sqlite3 zlib"
RDEPENDS_${PN} = " \
		libpcap \
		libnetfilter-log \
		sqlite3 \
        "

PR = "r2"

SRC_URI = "http://www.pmacct.net/pmacct-${PV}.tar.gz \
	file://pmacctd.init \
    file://pmacctd.conf \
    file://pmacctd.default \
    file://pmacctd.service \
    file://nfacctd.init \
    file://nfacctd.conf \
    file://nfacctd.default \
    file://nfacctd.service \
    file://sfacctd.init \
    file://sfacctd.conf \
    file://sfacctd.default \
    file://sfacctd.service \
    file://uacctd.init \
    file://uacctd.conf \
    file://uacctd.default \
    file://uacctd.service \
    "

SRC_URI[md5sum] = "582182c413e578a535c4f293355f3d19"
SRC_URI[sha256sum] = "19c3795db452191c2b1b9533fecaf69c6767c9fb7b4ae60ae3f28e24eb2ee9c8"

LIC_FILES_CHKSUM = "file://COPYING;md5=4ad4258dc4185a6fd4ddc24d9b4067f3"

inherit autotools-brokensep systemd

# Note: don't start pmacct on boot, VyOS will handle that...

# Without 'pcap-includes'  it'll check for the headers in /usr/include
# TODO: check which database pmacct uses in VyOS, for now we enable sqlite3
EXTRA_OECONF = " \
    --with-pcap-includes=${STAGING_INCDIR} \
	--disable-pgsql \
	--disable-mysql \
	--enable-sqlite3 \
	--enable-ipv6 \
	--enable-64bit \
	--enable-threads \
	--disable-jansson \
	--disable-geoip \
	--disable-rabbitmq \
	--disable-kafka \
    --enable-nflog \
    --disable-st-bins \
    "

do_install_append() {
	install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/pmacct
    install -d ${D}${sysconfdir}/default
    install -d ${D}${base_libdir}/systemd/system/
    for i in pmacctd nfacctd sfacctd uacctd; do \
        install -m 755 ${WORKDIR}/${i}.init ${D}${sysconfdir}/init.d/${i}; \
        install -m 644 ${WORKDIR}/${i}.conf ${D}${sysconfdir}/pmacct; \
        install -m 644 ${WORKDIR}/${i}.default ${D}${sysconfdir}/default/${i}; \
		install -m 644 ${WORKDIR}/${i}.service ${D}${systemd_unitdir}/system
    done

    # save a copy of default config files (from vyos-netflow postinst)
    cp ${D}${sysconfdir}/pmacct/pmacctd.conf ${D}${sysconfdir}/pmacct/pmacctd.conf.bak
}

SYSTEMD_SERVICE_${PN} = "pmacctd.service nfacctd.service sfacctd.service uacctd.service"
SYSTEMD_AUTO_ENABLE = "disable"
