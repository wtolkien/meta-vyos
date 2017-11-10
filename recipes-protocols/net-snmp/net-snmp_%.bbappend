RDEPENDS_${PN} += " net-snmp-server-snmpd"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://init \
    file://snmpd.default \
    file://snmpd.conf \
    "

# disable /etc/init.d/snmpd startup script (VyOS takes care of this)
INITSCRIPT_PARAMS_${PN}-server-snmpd = "remove"

do_install_append() {
    install -d ${D}${sysconfdir}/default
    install ${WORKDIR}/snmpd.default ${D}${sysconfdir}/default/snmpd
    install -d ${D}${sysconfdir}/snmp
    touch ${D}${sysconfdir}/snmp/snmp.conf
}

FILES_${PN}-server-snmpd += " ${sysconfdir}/default/snmpd"
FILES_${PN}-client += " ${sysconfdir}/snmp/snmp.conf"
