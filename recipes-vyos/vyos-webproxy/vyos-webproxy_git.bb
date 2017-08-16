SUMMARY = "VyOS webproxy config & op templates/scripts"
HOMEPAGE = "https://github.com/vyos/vyatta-webproxy"
SECTION = "vyos/proxy"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=16de935ebcebe2420535844d4f6faefc"

SRC_URI = "git://github.com/vyos/vyatta-webproxy.git;branch=current;protocol=https \
	  "

# snapshot from Aug 15, 2017:
SRCREV = "f3e8692d3eaf6a4c685f0358af8d3e961b1685e5"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"
RDEPENDS_${PN} = "perl vyos-cfg vyos-cfg-system vyos-op squid squidguard"

FILES_${PN} += "/opt"


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


# perform some post-installation actions, but only on target device, not at
# build time
pkg_postinst_${PN} () {
	if [ x"$D" = "x" ]; then

# TODO: check me!

#prefix=/opt/vyatta
#exec_prefix=${prefix}
#sysconfdir=${prefix}/etc
#bindir=${exec_prefix}/bin
#sbindir=${exec_prefix}/sbin
#urlfilterdatadir=/opt/vyatta/etc/config/url-filtering


# don't start squid on boot
#update-rc.d -f squid3 remove >/dev/null

# save a copy of default config files
#cp /etc/squid3/squid.conf /etc/squid3/squid.conf.bak
#cp /etc/squid/squidGuard.conf /etc/squid/squidGuard.conf.bak
#sed -i 's/nocreate/create 640 proxy adm/' /etc/logrotate.d/squid3
#sed -i 's/delaycompress//' /etc/logrotate.d/squid3
#sed -i 's/compress//' /etc/logrotate.d/squid3
#sed -i 's/etc\/init.d\/squid/etc\/init.d\/squid3/' /usr/sbin/update-squidguard
#sed -i 's/reload/restart/' /usr/sbin/update-squidguard
#chown proxy:adm /var/log/squid

# create local conf placeholder
#touch /etc/squid3/local.conf

# mv databases to global data directory
#if [ ! -d $urlfilterdatadir ]; then
#    mkdir -p $urlfilterdatadir
#fi
#if [ -d /var/lib/squidguard ]; then
#    mv /var/lib/squidguard $urlfilterdatadir
#fi
#if [ -d /var/lib/sitefilter ]; then
#    mv /var/lib/sitefilter $urlfilterdatadir
#fi
	else
		exit 1
	fi
}
