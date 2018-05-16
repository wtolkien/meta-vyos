# use startup script provided by VyOS
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# disable /etc/init.d/xl2tpd startup script (VyOS takes care of this)
SYSTEMD_AUTO_ENABLE = "disable"
# obsolete for sysvinit
#INITSCRIPT_PARAMS_${PN} = "remove"
