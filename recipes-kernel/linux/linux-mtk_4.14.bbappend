# add VyOS patches
FILESEXTRAPATHS_prepend := "${THISDIR}/vyos-kernel-patches-4.14:"

SRC_URI += " \
    file://0700-allow-pkts-on-disabled-interfaces.patch \
    file://0701-inotify-support-for-stackable-filesystems.patch \
    "
