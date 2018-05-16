FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

do_install_append () {
    install -d ${D}${sysconfdir}
    install -m 0640 ${WORKDIR}/fuse.conf ${D}${sysconfdir}

    # bugfix: OE recipe installs fuse.conf in modules-load.d
    rm -rf ${D}${sysconfdir}/modules-load.d/fuse.conf
}

# 'fuse' gets renamed to 'libfuse2' according to Debian package naming
# conventions. Debian renaming is default behavior for all packages since
# the 'debian' bbclass is inherited by default. As a result,
# pkg_postinst_ontarget_${PN} doens't work.
# Referencing _libfuse2 instead of _${PN} doens't work either (Yocto bug?)
# As a workaround we disable all Debian renaming - hopefully this won't
# break anything else

DEBIAN_NOAUTONAME_fuse-dbg = "1"
DEBIAN_NOAUTONAME_fuse-staticdev = "1"
DEBIAN_NOAUTONAME_fuse-dev = "1"
DEBIAN_NOAUTONAME_fuse-doc = "1"
DEBIAN_NOAUTONAME_fuse-locale = "1"
DEBIAN_NOAUTONAME_fuse = "1"


# mirror what VyOS does - disable as a service
pkg_postinst_${PN} () {
	if [ -n "$D" ]; then
		OPTS="--root=$D"
	fi
	systemctl $OPTS mask ${PN}.service
}

pkg_postinst_ontarget_${PN} () {
    chgrp users /etc/fuse.conf
}
