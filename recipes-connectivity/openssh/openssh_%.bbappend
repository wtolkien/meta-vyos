FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# Note #1: by default, Yocto sshd uses the traditional /etc/init.d/sshd script
# for startup, relying on systemd-sysv-generator on systems that use systemd.
# However, VyOS disables the sshd service, then starts sshd by calling
# invokerc.d on demand. Unfortunately invokerc.d does not start sshd when
# it's disabled by systemd and using the sysv-generator mechanism.
# It (invokerc.d) does however work when systemd uses a native sshd.service
# file. That's why we provide one here.
#
# Note #2: VyOS generates sshd keys as part of the vyatta-router service which
# calls '/opt/vyatta/sbin/rl-system.init' which calls 'ssh-keygen'. Since Yocto
# provides the sshdgenkeys.service, we patch vyatta-router.service and
# rl-system.init to do things the Yocto way.
#
# Note #3: VyOS expects the sshd service to be called 'ssh', Yocto names it
# 'sshd'. We patch service/ssh/node.def in the vyos-cfg-system package to
# account for this
#
# Note #4: The no-longer-used /etc/init.d/sshd script normally creates the
# /var/run/sshd directory required by sshd. This functionality has been moved
# into sshdgenkeys.service provided here.

SRC_URI += " \
		file://sshd.service \
		"

# for some reason, VyOS sill supports SSH v1. TODO: remove v1 functionality
EXTRA_OECONF += " --with-ssh1"

# disable /etc/init.d/sshd startup script (VyOS takes care of this)
SYSTEMD_AUTO_ENABLE_${PN}-sshd = "disable"

do_install_append () {
	cp ${WORKDIR}/sshd.service ${D}/${systemd_unitdir}/system
}
