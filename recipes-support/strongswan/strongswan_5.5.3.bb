DESCRIPTION = "strongSwan is an OpenSource IPsec implementation for the \
Linux operating system."
SUMMARY = "strongSwan is an OpenSource IPsec implementation"
HOMEPAGE = "http://www.strongswan.org"
SECTION = "net"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
DEPENDS = "gmp openssl flex-native flex bison-native"

SRC_URI = "http://download.strongswan.org/strongswan-${PV}.tar.bz2 \
        file://fix-funtion-parameter.patch \
        file://0001-adhere-to-IKE_SA-limit.patch \
        file://0002-charon-add-opt-src.patch \
        file://0003-vici-send-certs-for-ike_sa-events.patch \
        file://0004-vici-support-indiv-sa-state-changes.patch \
        file://0005-vici-support-async-initiation.patch \
        file://0006-terminate-by-src-and-dst.patch \
        file://libstrongswan.strongswan.logcheck.ignore.paranoid \
        file://libstrongswan.strongswan.logcheck.ignore.server \
        file://libstrongswan.strongswan.logcheck.ignore.workstation \
        file://libstrongswan.strongswan.logcheck.violations.ignore \
        file://strongswan-starter.ipsec.init \
        "

SRC_URI[md5sum] = "4afffe3c219bb2e04f09510905af836b"
SRC_URI[sha256sum] = "c5ea54b199174708de11af9b8f4ecf28b5b0743d4bc0e380e741f25b28c0f8d4"

EXTRA_OECONF = " \
        --disable-blowfish --disable-des \
        --without-lib-prefix \
        --enable-agent \
        --enable-ctr --enable-ccm --enable-gcm --enable-addrblock \
        --with-capabilities=libcap \
        --enable-farp \
        --enable-dhcp \
        --enable-af-alg \
        --enable-vici \
        --enable-ha \
		--enable-led --enable-gcrypt \
		--enable-test-vectors \
		--enable-xauth-eap --enable-xauth-pam --enable-xauth-noauth \
        --enable-cmd \
		--enable-certexpire \
		--enable-lookip \
		--enable-error-notify \
		--enable-unity \
        --enable-eap-radius --enable-eap-identity --enable-eap-md5 \
		--enable-eap-gtc --enable-eap-aka --enable-eap-mschapv2 \
		--enable-eap-tls --enable-eap-ttls --enable-eap-tnc \
        "

# TODO: the following will cause install paths for charon, etc to match what they
# are on VyOS 1.2, however we may want to keep the original OE paths...
EXTRA_OECONF += " \
        --libexecdir=/usr/lib \
        "

#--with-user=strongswan --with-group=nogroup
#	--enable-kernel-pfkey --enable-kernel-klips \
# And for --enable-eap-sim we would need the library, which we don't
# have right now.
# Don't --enable-cisco-quirks, because some other IPsec implementations
# (most notably the Phion one) have problems connecting when pluto
# sends these Cisco options.

EXTRA_OECONF += "${@bb.utils.contains('DISTRO_FEATURES', 'systemd', '--with-systemdsystemunitdir=${systemd_unitdir}/system/', '--without-systemdsystemunitdir', d)}"


PACKAGECONFIG ??= "aesni charon curl gmp openssl stroke \
        swanctl connmark pkcs11 scep \
        ${@bb.utils.filter('DISTRO_FEATURES', 'ldap', d)} \
"
PACKAGECONFIG[aesni] = "--enable-aesni,--disable-aesni,,${PN}-plugin-aesni"
PACKAGECONFIG[charon] = "--enable-charon,--disable-charon,"
PACKAGECONFIG[curl] = "--enable-curl,--disable-curl,curl,${PN}-plugin-curl"
PACKAGECONFIG[gmp] = "--enable-gmp,--disable-gmp,gmp,${PN}-plugin-gmp"
PACKAGECONFIG[ldap] = "--enable-ldap,--disable-ldap,openldap,${PN}-plugin-ldap"
PACKAGECONFIG[mysql] = "--enable-mysql,--disable-mysql,mysql5,${PN}-plugin-mysql"
PACKAGECONFIG[openssl] = "--enable-openssl,--disable-openssl,openssl,${PN}-plugin-openssl"
PACKAGECONFIG[scep] = "--enable-scepclient,--disable-scepclient,"
PACKAGECONFIG[soup] = "--enable-soup,--disable-soup,libsoup-2.4,${PN}-plugin-soup"
PACKAGECONFIG[sqlite3] = "--enable-sqlite,--disable-sqlite,sqlite3,${PN}-plugin-sqlite"
PACKAGECONFIG[stroke] = "--enable-stroke,--disable-stroke,,${PN}-plugin-stroke"
PACKAGECONFIG[swanctl] = "--enable-swanctl,--disable-swanctl,,libgcc"
PACKAGECONFIG[mediation] = "--enable-mediation --enable-medcli --enable-medsrv, \
                            --disable-mediation --disable-medcli --disable-medsrv, \
                            clearsilver, clearsilver"
PACKAGECONFIG[connmark] = "--enable-connmark, --disable-connmark, iptables, iptables"
PACKAGECONFIG[pkcs11] = "--enable-pkcs11, --disable-pkcs11, ,${PN}-plugin-pkcs11"
PACKAGECONFIG[fast] = "--enable-fast, --disable-fast, ,${PN}-plugin-fast"
PACKAGECONFIG[tpm] = "--enable-tpm, --disable-tpm,"


# requires swanctl
PACKAGECONFIG[systemd-charon] = "--enable-systemd,--disable-systemd,systemd,"

inherit autotools systemd pkgconfig

RRECOMMENDS_${PN} = "kernel-module-ipsec"

FILES_${PN} += "/usr/lib"
FILES_${PN} += "${libdir}/ipsec/lib*${SOLIBS}"
FILES_${PN}-dbg += "${bindir}/.debug ${libdir}/ipsec/.debug ${libexecdir}/ipsec/.debug"
FILES_${PN}-dev += "${libdir}/ipsec/lib*${SOLIBSDEV} ${libdir}/ipsec/*.la"
FILES_${PN}-staticdev += "${libdir}/ipsec/*.a"

CONFFILES_${PN} = "${sysconfdir}/*.conf ${sysconfdir}/ipsec.d/*.conf ${sysconfdir}/strongswan.d/*.conf"

PACKAGES += "${PN}-plugins"
ALLOW_EMPTY_${PN}-plugins = "1"

PACKAGES_DYNAMIC += "^${PN}-plugin-.*$"
NOAUTOPACKAGEDEBUG = "1"

do_compile_append () {
    rm -rf ${D}/usr/sbin/.debug
}

# emulate what the VyOS strongswan package does, without necessarily knowning why...
do_install_append () {
    install -d ${D}/etc/ipsec.d/policies
    install -d ${D}/var/lib/strongswan
    install -d ${D}/etc/init.d
    cp ${WORKDIR}/strongswan-starter.ipsec.init ${D}/etc/init.d/ipsec
    install -d ${D}/etc/logcheck/ignore.d.paranoid
    cp ${WORKDIR}/libstrongswan.strongswan.logcheck.ignore.paranoid ${D}/etc/logcheck/ignore.d.paranoid/strongswan
    install -d ${D}/etc/logcheck/ignore.d.server
    cp ${WORKDIR}/libstrongswan.strongswan.logcheck.ignore.server ${D}/etc/logcheck/ignore.d.server/strongswan
    install -d ${D}/etc/logcheck/ignore.d.workstation
    cp ${WORKDIR}/libstrongswan.strongswan.logcheck.ignore.workstation ${D}/etc/logcheck/ignore.d.workstation/strongswan
    install -d ${D}/etc/logcheck/violations.ignore.d
    cp ${WORKDIR}/libstrongswan.strongswan.logcheck.violations.ignore ${D}/etc/logcheck/violations.ignore.d/strongswan
}


python split_strongswan_plugins () {
    sysconfdir = d.expand('${sysconfdir}/strongswan.d/charon')
    libdir = d.expand('${libdir}/ipsec/plugins')
    dbglibdir = os.path.join(libdir, '.debug')

    def add_plugin_conf(f, pkg, file_regex, output_pattern, modulename):
        dvar = d.getVar('PKGD', True)
        oldfiles = d.getVar('CONFFILES_' + pkg, True)
        newfile = '/' + os.path.relpath(f, dvar)

        if not oldfiles:
            d.setVar('CONFFILES_' + pkg, newfile)
        else:
            d.setVar('CONFFILES_' + pkg, oldfiles + " " + newfile)

    split_packages = do_split_packages(d, libdir, 'libstrongswan-(.*)\.so', '${PN}-plugin-%s', 'strongSwan %s plugin', prepend=True)
    do_split_packages(d, sysconfdir, '(.*)\.conf', '${PN}-plugin-%s', 'strongSwan %s plugin', prepend=True, hook=add_plugin_conf)

    split_dbg_packages = do_split_packages(d, dbglibdir, 'libstrongswan-(.*)\.so', '${PN}-plugin-%s-dbg', 'strongSwan %s plugin - Debugging files', prepend=True, extra_depends='${PN}-dbg')
    split_dev_packages = do_split_packages(d, libdir, 'libstrongswan-(.*)\.la', '${PN}-plugin-%s-dev', 'strongSwan %s plugin - Development files', prepend=True, extra_depends='${PN}-dev')
    split_staticdev_packages = do_split_packages(d, libdir, 'libstrongswan-(.*)\.a', '${PN}-plugin-%s-staticdev', 'strongSwan %s plugin - Development files (Static Libraries)', prepend=True, extra_depends='${PN}-staticdev')

    if split_packages:
        pn = d.getVar('PN', True)
        d.setVar('RRECOMMENDS_' + pn + '-plugins', ' '.join(split_packages))
        d.appendVar('RRECOMMENDS_' + pn + '-dbg', ' ' + ' '.join(split_dbg_packages))
        d.appendVar('RRECOMMENDS_' + pn + '-dev', ' ' + ' '.join(split_dev_packages))
        d.appendVar('RRECOMMENDS_' + pn + '-staticdev', ' ' + ' '.join(split_staticdev_packages))
}

PACKAGESPLITFUNCS_prepend = "split_strongswan_plugins "

# Install some default plugins based on default strongSwan ./configure options
# See https://wiki.strongswan.org/projects/strongswan/wiki/Pluginlist
RDEPENDS_${PN} += "\
    ${PN}-plugin-aes \
    ${PN}-plugin-attr \
    ${PN}-plugin-cmac \
    ${PN}-plugin-constraints \
    ${PN}-plugin-des \
    ${PN}-plugin-dnskey \
    ${PN}-plugin-hmac \
    ${PN}-plugin-kernel-netlink \
    ${PN}-plugin-md5 \
    ${PN}-plugin-nonce \
    ${PN}-plugin-pem \
    ${PN}-plugin-pgp \
    ${PN}-plugin-pkcs1 \
    ${PN}-plugin-pkcs7 \
    ${PN}-plugin-pkcs8 \
    ${PN}-plugin-pkcs12 \
    ${PN}-plugin-pubkey \
    ${PN}-plugin-random \
    ${PN}-plugin-rc2 \
    ${PN}-plugin-resolve \
    ${PN}-plugin-revocation \
    ${PN}-plugin-sha1 \
    ${PN}-plugin-sha2 \
    ${PN}-plugin-socket-default \
    ${PN}-plugin-sshkey \
    ${PN}-plugin-updown \
    ${PN}-plugin-vici \
    ${PN}-plugin-x509 \
    ${PN}-plugin-xauth-generic \
    ${PN}-plugin-xcbc \
    "

RPROVIDES_${PN} += "${PN}-systemd"
RREPLACES_${PN} += "${PN}-systemd"
RCONFLICTS_${PN} += "${PN}-systemd"
SYSTEMD_SERVICE_${PN} = "${@bb.utils.contains('PACKAGECONFIG', 'swanctl', '${BPN}-swanctl.service', '${BPN}.service', d)}"
