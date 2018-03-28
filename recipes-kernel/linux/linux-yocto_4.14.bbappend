
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${LINUX_VERSION}:"
FILESEXTRAPATHS_prepend := "${THISDIR}/vyos-kernel-patches-4.14:"

SRC_URI += " \
    file://0700-allow-pkts-on-disabled-interfaces.patch \
    file://0701-inotify-support-for-stackable-filesystems.patch \
    file://defconfig \
    "


DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"

LINUX_VERSION_EXTENSION = "meta-vyos"

# remove default stuff which we don't need...
KERNEL_FEATURES_remove = " \
    features/nfsd/nfsd-enable.scc \
    features/sound/snd_hda_intel.scc \
    cfg/sound.scc \
    "

# now add stuff that we do actually need
KERNEL_FEATURES_append = " \
    features/net/net.scc \
    features/gre/gre-enable.scc \
    features/crypto/crypto.scc \
    features/fuse/fuse.scc \
    features/debug/printk.scc \
    features/netfilter/netfilter.scc \
    features/nf_tables/nf_tables.scc \
    "
