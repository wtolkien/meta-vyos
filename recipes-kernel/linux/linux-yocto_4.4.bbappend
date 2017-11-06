
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

LINUX_VERSION = "4.4.47"
#SRCREV_machine_qemux86 = "9aa404b2a969c4cb5d4cbff92209e1c0943bec37"
SRCREV_machine_qemux86 = "4686ea264f1dfec6bc5db9ef4bb9ed5babbb78cd"
SRC_URI += " \
    file://linux-4-4-47-vyos-additions.patch \
    file://defconfig \
    "
#    file://fragment.cfg
#    file://kernel-meta/bsp/common-pc/common-pc-qemuvyos.scc

LINUX_VERSION_EXTENSION = "meta-vyos"

#KMACHINE = "qemuvyos"

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
