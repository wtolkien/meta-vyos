SUMMARY = "VyOS VPN configuration"
HOMEPAGE = "https://github.com/vyos/vyatta-cfg-vpn"
SECTION = "vyos/vpn"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-cfg-vpn.git;branch=current;protocol=https \
	file://001-fix-logrotate-permissions.patch \
	"

# snapshot from Aug 5, 2017:
SRCREV = "18f30fbda88e075fbd48459f2f6d646ba333ff3c"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash vyos-cfg"
RDEPENDS_${PN} = "strongswan libcrypt-openssl-rsa-perl"

FILES_${PN} += "/opt /usr/lib"

# NOTE: this software seems not capable of being built in a separate build directory
# from the source, therefore using 'autotools-brokensep' instead of 'autotools'
inherit autotools-brokensep

# additional options to be passed to the configure script:
EXTRA_OECONF = "\
    --prefix=/opt/vyatta \
    --exec_prefix=/opt/vyatta \
	--sbindir=/opt/vyatta/sbin \
	--bindir=/opt/vyatta/bin \
	--datadir=/opt/vyatta/share \
	--sysconfdir=/opt/vyatta/etc \
	"

pkg_postinst_ontarget_${PN} () {
	# remove init of daemons that we start/stop
	for init in openswan ipsec setkey; do
		update-rc.d -f ${init} remove >/dev/null
	done

	# remove keys
	rm -f /etc/ipsec.secrets
	touch /etc/ipsec.secrets
	chown root:root /etc/ipsec.secrets
	chmod 600 /etc/ipsec.secrets
	rm -f /etc/ipsec.d/private/localhost.localdomainKey.pem
	rm -f /etc/ipsec.d/certs/localhost.localdomainCert.pem
}
