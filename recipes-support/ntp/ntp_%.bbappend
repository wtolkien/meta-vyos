RDEPENDS_${PN} += " sntp ntp-utils ntpdate"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://ntp.default \
    file://ntp.dhclient-exit-hooks \
    file://ntp.cron \
    "

PACKAGECONFIG ??= "cap openssl \
    ${@bb.utils.filter('DISTRO_FEATURES', 'ipv6', d)} \
"

# disable /etc/init.d/ntp startup script (VyOS takes care of this)
SYSTEMD_AUTO_ENABLE_${PN} = "disable"
SYSTEMD_AUTO_ENABLE_ntpdate = "disable"
SYSTEMD_AUTO_ENABLE_sntp = "disable"

do_install_append() {
    install -d ${D}${sysconfdir}/default
    install ${WORKDIR}/ntp.default ${D}${sysconfdir}/default/ntp
    install -d ${D}${sysconfdir}/dhcp/dhclient-exit-hooks.d
    install ${WORKDIR}/ntp.dhclient-exit-hooks ${D}${sysconfdir}/dhcp/dhclient-exit-hooks.d/ntp
    install -d ${D}${sysconfdir}/cron.daily
    install ${WORKDIR}/ntp.cron ${D}${sysconfdir}/cron.daily/ntp

    install -d ${D}${localstatedir}/volatile/log/ntpstats

    for i in ntpdc ntpq ntpsweep ntptrace sntp; do \
        ln -s ${sbindir}/${i} ${D}${bindir}; \
    done
}

FILES_${PN} += " ${localstatedir}/volatile ${bindir} ${sysconfdir}"
