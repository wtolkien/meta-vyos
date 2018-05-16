FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://conntrackd.conf \
	file://conntrackd.service \
    file://conntrackd.logrotate \
    "

do_install_append () {
    install -m 0644 ${WORKDIR}/conntrackd.conf ${D}/${sysconfdir}/conntrackd

	install -d ${D}${base_libdir}/systemd/system
	install -m 0644 ${WORKDIR}/conntrackd.service ${D}${base_libdir}/systemd/system

    install -d ${D}${sysconfdir}/logrotate.d
	install -m 0644 ${WORKDIR}/conntrackd.logrotate ${D}${sysconfdir}/logrotate.d/conntrackd

    rm ${D}/${sysconfdir}/conntrackd/conntrackd.conf.sample
}

FILES_${PN} += " /lib/systemd/system"


# disable conntrack-failover service
# TODO: VyOS uses conntrack-tools v1.4.2, which does not have conntrack-failover. Check
# what to do with it...
pkg_postinst_${PN} () {
	if [ -n "$D" ]; then
		OPTS="--root=$D"
	fi
	systemctl $OPTS mask conntrack-failover.service
}
