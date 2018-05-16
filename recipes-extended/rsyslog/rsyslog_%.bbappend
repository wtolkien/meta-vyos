# Yocto's sysv startup script is /etc/init.d/syslog, the systemd
# service file is called 'rsyslog.service'. If we don't disable
# bitbake's update-rc class, then the rsyslog service gets started
# (and restarted) multiple time during startup because sysv-generator
# creates an additional 'syslog.service' file.

INHIBIT_UPDATERCD_BBCLASS = "1"

# VyOS provides it's own logrotate rules which conflict ("auth.log") with the ones
# provided by this package in /etc/logrotate.d/logrotate.rsyslog.
# TODO: check if it's better to remove the 'auth.log' line from this file rather 
# than deleting it entirely

do_install_append () {
	rm -rf ${D}${sysconfdir}/logrotate.d
}
