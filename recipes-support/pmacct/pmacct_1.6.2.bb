DESCRIPTION = "Promiscuous mode IP Accounting package"
HOMEPAGE = "http://www.pmacct.net/"
LICENSE = "GPLv2"
DEPENDS = "libpcap libnetfilter-log gettext-native autogen-native \
        autoconf-native libtool-native"
RDEPENDS_${PN} = "libpcap libnetfilter-log"
PR = "r2"


SRC_URI = "http://www.pmacct.net/pmacct-${PV}.tar.gz \
	file://pmacct.init"

SRC_URI[md5sum] = "5f6d2c0fa1d045dde2bc26378899b3a8"
SRC_URI[sha256sum] = "e6ede7f500fb1771b5cdfb63dfa016e34c19b8aa2d2f672bd4c63016a5d6bbe2"

LIC_FILES_CHKSUM = "file://COPYING;md5=4ad4258dc4185a6fd4ddc24d9b4067f3"

inherit autotools-brokensep

# Without 'pcap-includes'  it'll check for the headers in /usr/include
# TODO: check which database pmacct uses in VyOS
EXTRA_OECONF = " \
    --with-pcap-includes=${STAGING_INCDIR} \
	--enable-mmap \
	--disable-pgsql \
	--with-pgsql-includes=`pg_config --includedir` \
	--disable-mysql \
	--disable-sqlite3 \
	--enable-ipv6 \
	--enable-v4-mapped \
	--enable-64bit \
	--enable-threads \
	--disable-jansson \
	--disable-geoip \
	--disable-rabbitmq \
	--disable-kafka \
    --enable-nflog \
    "

do_install_append() {
	install -d ${D}${sysconfdir}/init.d/
	install -d ${D}${sysconfdir}/pmacct
	install -m 755 ${WORKDIR}/pmacct.init ${D}${sysconfdir}/init.d/pmacct
}

# TODO (from vyos-netflow postinst:)
# don't start pmacct on boot
#update-rc.d -f pmacct remove >/dev/null

# save a copy of default config files
#cp /etc/pmacct/pmacctd.conf /etc/pmacct/pmacctd.conf.bak
