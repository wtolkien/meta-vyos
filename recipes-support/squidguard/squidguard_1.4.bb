DESCRIPTION = "Squid URL redirector"
HOMEPAGE = "http://www.squidguard.org/"
SECTION = "network"
DEPENDS = "gettext-native autoconf-native libtool-native \
		db openldap mysql5 zlib"
RDEPENDS_${PN} += "squid db perl liburi-perl libwww-perl"
LICENSE = "GPLv2"
PR = "r2"

SRC_URI = " \
	http://www.squidguard.org/Downloads/squidGuard-${PV}.tar.gz;name=tar \
	file://squidguard-1.4-no_header_checks.patch \
	file://squidguard-cross-ldap.patch \
	file://squidguard-1.4-fix-parallel-build.patch \
	file://squidguard-1.4-fix-ac-configure-errors.patch \
	file://squidguard-fix-CVE-2009-3700.patch \
	file://squidguard-fix-CVE-2009-3826.patch \
	file://squidguard-1.4-vyos-https-logging.patch \
	file://squidguard-1.4-vyos-add-external-classifier-intf.patch \
	file://squidGuard.conf \
	"

SRC_URI[tar.md5sum] = "de834150998c1386c30feae196f16b06"
SRC_URI[tar.sha256sum] = "0711ce60b8e2bbba107b980fed446a88df35e1584b39f079c0cae54a172c5141"

LIC_FILES_CHKSUM = "file://COPYING;md5=17cccb55725bad30d60ee344fa9561e6"

S = "${WORKDIR}/squidGuard-${PV}"

EXTRA_OECONF += " \
	--with-squiduser=nobody \
	--with-db=${STAGING_INCDIR}/.. \
	--with-sg-config=${sysconfdir}/squid/squidGuard.conf \
	--with-sg-logdir=${localstatedir}/log/squid \
	--with-sg-dbhome=${localstatedir}/lib/squidguard/db \
	--with-ldap=yes \
	--with-ldap-inc=${STAGING_INCDIR} \
	--with-ldap-lib=${STAGING_LIBDIR} \
	--with-mysql=${STAGING_INCDIR}/.. \
	"

inherit autotools-brokensep

do_configure_prepend() {
	export ac_cv_header_db_h=yes
	export db_ok_version=yes
	export dbg3_ok_version=yes
	export dbg2_ok_version=yes
#	cp src/config.h.in src/config.h.in.original
}

#do_configure_append() {
#	mv src/config.h.in.original src/config.h.in
#	./config.status
#}

do_install() {
	install -d ${D}${bindir}
	install -d ${D}${sysconfdir}/squid
	install -d ${D}${localstatedir}/log/squid
	install -d ${D}${localstatedir}/lib/squidguard/db
	install -m 0755 src/squidGuard ${D}${bindir}
	install -m 0644 ${WORKDIR}/squidGuard.conf ${D}${sysconfdir}/squid/squidGuard.conf
}

pkg_postinst_ontarget_${PN} () {
# TODO: check what needs to be done here, for now just leave one dummy cmd...
	DUMMY=1

#CONF="/etc/default/squidguard"

#. /usr/share/debconf/confmodule

#db_get squidguard/dbreload
#echo "DBRELOAD=\"$RET\"" > $CONF
#db_stop

#if [ -f "$CONF" ]; then
#        . "$CONF"
#fi

#case "$1" in
#    configure)

	  # previously, the code here was to update squidguard database using
	  # /usr/sbin/update-squidguard when [ "$DBRELOAD" = "true" ]. Vyatta does
	  # not want to do that since database is updated using an op-mode command
	  # For more information see http://bugzilla.vyatta.com/show_bug.cgi?id=5329

#    ;;

#    abort-upgrade|abort-remove|abort-deconfigure)

#    ;;

#    *)
#        echo "postinst called with unknown argument \`$1'" >&2
#        exit 0
#    ;;
#esac

#exit 0
}
