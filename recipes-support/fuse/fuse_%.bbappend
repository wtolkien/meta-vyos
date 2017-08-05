FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

do_install_append () {
    install -d ${D}${sysconfdir}
    install -m 0640 ${WORKDIR}/fuse.conf ${D}${sysconfdir}
}

# perform some post-installation actions related to file ownership
pkg_postinst_${PN} () {
	  if [ x"$D" = "x" ]; then
		    chgrp users /etc/fuse.conf
    else
        exit 1
    fi
}
