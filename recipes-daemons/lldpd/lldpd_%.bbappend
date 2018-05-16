
# disable /etc/init.d/lldpd startup script (VyOS takes care of this)
SYSTEMD_AUTO_ENABLE_${PN} = "disable"
# obsolete (for sysvinit):
#INITSCRIPT_PARAMS_${PN} = "remove"
