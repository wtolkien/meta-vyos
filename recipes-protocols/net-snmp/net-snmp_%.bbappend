RDEPENDS_${PN} += " net-snmp-server-snmpd"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://init \
    file://snmpd.default \
    file://snmpd.conf \
    "

PACKAGECONFIG_append = " perl"

# disable /etc/init.d/snmpd startup script (VyOS takes care of this)
SYSTEMD_AUTO_ENABLE_${PN}-server-snmpd = "disable"
SYSTEMD_AUTO_ENABLE_${PN}-server-snmptrapd = "disable"
# obsolete for sysvinit:
#INITSCRIPT_PARAMS_${PN}-server-snmpd = "remove"

do_install_append() {
    install -d ${D}${sysconfdir}/default
    install ${WORKDIR}/snmpd.default ${D}${sysconfdir}/default/snmpd
    install -d ${D}${sysconfdir}/snmp
    touch ${D}${sysconfdir}/snmp/snmp.conf
}

FILES_${PN}-server-snmpd += " ${sysconfdir}/default/snmpd"
FILES_${PN}-client += " ${sysconfdir}/snmp/snmp.conf"
