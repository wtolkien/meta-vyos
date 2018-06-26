FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

ALTERNATIVE_${PN} += " telinit"

ALTERNATIVE_TARGET[telinit] = "${base_bindir}/systemctl"
ALTERNATIVE_LINK_NAME[telinit] = "${base_sbindir}/telinit"
ALTERNATIVE_PRIORITY[telinit] ?= "300"

RDEPENDS_${PN} += " ${PN}-analyze"

SRC_URI += " \
    file://journald.conf \
    file://logind.conf \
    file://aftervyos.conf \
    file://noclear.conf \
    file://system.conf \
    "

do_install_append () {
    install -d ${D}${sysconfdir}/systemd/system/getty@.service.d
    install -d ${D}${sysconfdir}/systemd/system/serial-getty@.service.d
    install -d ${D}${sysconfdir}/systemd/system/getty@tty1.service.d

    cp ${WORKDIR}/journald.conf ${D}${sysconfdir}/systemd
    cp ${WORKDIR}/logind.conf ${D}${sysconfdir}/systemd
    cp ${WORKDIR}/system.conf ${D}${sysconfdir}/systemd
    cp ${WORKDIR}/aftervyos.conf ${D}${sysconfdir}/systemd/system/getty@.service.d
    cp ${WORKDIR}/aftervyos.conf ${D}${sysconfdir}/systemd/system/serial-getty@.service.d
    cp ${WORKDIR}/noclear.conf ${D}${sysconfdir}/systemd/system/getty@tty1.service.d
}

# disable a few more systemd startup services...
SYSTEMD_MASKED_SCRIPTS = " \
    systemd-networkd-wait-online \
    systemd-timesyncd \
    plymouth-quit-wait \
    plymouth-start \
    display-manager \
    auditd \
    rmnologin \
    "

pkg_postinst_${PN} () {
	if [ -n "$D" ]; then
		OPTS="--root=$D"
	fi
	for SERVICE in ${SYSTEMD_MASKED_SCRIPTS}; do
		systemctl $OPTS mask $SERVICE.service
	done
}
