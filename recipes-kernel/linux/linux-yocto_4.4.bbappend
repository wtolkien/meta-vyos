
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

LINUX_VERSION = "4.4.47"
#SRCREV_machine_qemux86 = "9aa404b2a969c4cb5d4cbff92209e1c0943bec37"
SRCREV_machine_qemux86 = "4686ea264f1dfec6bc5db9ef4bb9ed5babbb78cd"
SRC_URI += " file://linux-4-4-47-vyos-additions.patch"
