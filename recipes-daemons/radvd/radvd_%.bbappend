# use startup script provided by VyOS
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# disable /etc/init.d/xl2tpd startup script (VyOS takes care of this)
INITSCRIPT_PARAMS_${PN} = "remove"
