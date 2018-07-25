# add VyOS patches

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-vyos-${LINUX_VERSION}:"

SRC_URI_append_vyos= " \
    file://0001-Vyatta-Add-linkstate-IP-device-attribute.patch \
    file://0002-Fix-missing-REPORTING-BUGS-file-for-make-kpkg.patch \
    file://0003-VyOS-add-inotify-support-for-stackable-filesystems-o.patch \
    "
