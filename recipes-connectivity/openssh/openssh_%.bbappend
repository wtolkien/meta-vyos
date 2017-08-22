# for some reason, VyOS sill supports SSH v1
EXTRA_OECONF += " --with-ssh1"

# disable /etc/init.d/sshd startup script (VyOS takes care of this)
INITSCRIPT_PARAMS_${PN}-sshd = "remove"

do_install_append () {
    ln -s sshd ${D}/${sysconfdir}/init.d/ssh
}
