# meta-vyos
A meta layer to bring VyOS router functionality to OpenEmbedded

VyOS is an open source network operating system based on GNU/Linux. It joins
multiple applications such as Quagga, ISC DHCPD, OpenVPN, StrongS/WAN and
others under a single management interface.

More information can be found at https://vyos.io

VyOS is based on Debian and currently available as a LiveCD/Install ISO for
physical and virtual 64-bit x86 machines and VMWare OVA for vSphere/vCloud Air.

So far there are no official builds for other processor architectures or embedded
platforms, however an ARM build for SolidRun's ClearFog platform does exist:

http://dev.packages.vyos.net/devices/clearfog/solidrun/

While Debian is available for many different processor architectures, it was never
explicitly designed for embedded systems. As a result, embedded platform support
lags far behind compared to other build environments such as OpenEmbedded.

This project aims to create a meta layer for OpenEmbedded that will make it
easy to run VyOS router software on a wide variety of embedded platforms.

The project is currently at a very early stage: Some core VyOS packages have been
converted to OpenEmbedded and it is possible to start up a VyOS image in Qemu. It
is possible to change configuration and, for example, configure an Ethernet port. 
Other features may or may not work yet

There is a lot more work to do and any help from interested parties is very welcome.

## Some of the immediate issues:

- More packages need to be converted, features need to be tested.
- Most VyOS source packages build with GNU autotools, however they don't allow 
  building outside of the source directory. This prevents the usage of the 
  'devtool' command that's useful for local developmend with OpenEmbedded/bitbake.
- Image management is not working, and it may never work in the same way usually
  does with Debian VyOS. Debian VyOS uses Debian's LiveCD architecture with
  initramfs/squashfs to handle multiple images. It is not clear yet how this
  translates into the embedded realm, or even if it makes sense at all.
- So far there are bitbake recipes for only a select few core VyOS packages,
  so functionality will be limited at best and more bitbake recipes will have to
  be created.
- There are likely going to be a fair number of other bugs that stem from
  differences between core Debian vs. OpenEmbedded packages.


## Rough roadmap:

- Port over more VyOS features by creating recipes for other VyOS packages
- Resolve issues related to image management / firmware upgrade. Either find a
  way to use the existing 'VyOS way' with OpenEmbedded, or design something new
  that possibly makes more sense in an embedded environment
- Deal with issues related to logging and frequent file system write access that
  could wear out an embedded flash filesystem
- Performance improvements: embedded devices often have slow disk access because 
  the underlaying technology is SDCards, etc. VyOS's CLI is disk-read heavy, 
  performance may be improved by copying the whole /opt/vyatta tree to a ramdisk
- Debug!


# Build Instructions:

Development is currently done against OpenEmbedded (release 'pyro'), however other
OpenEmbedded-based distributions will likely work as well. If you are not familiar
with OpenEmbedded (or the 'Yocto' derivative), extensive documentation can be found 
here:

https://www.yoctoproject.org/documentation

* make sure you install the required packages as described here:

https://www.openembedded.org/wiki/Getting_started#Systems_based_upon_OE-Core

* get the OpenEmbedded core packages, the 'meta-openembedded' layer and the 'bitbake'
sources:
```
git clone git://git.openembedded.org/openembedded-core oe-core
cd oe-core
git clone git://git.openembedded.org/meta-openembedded
git clone git://git.openembedded.org/bitbake bitbake
git checkout pyro
cd meta-openembedded
git checkout pyro
cd ..
cd bitbake
git checkout 1.34
cd ..
````
* get this project's source:
```
git clone https://github.com/wtolkien/meta-vyos.git
```
* from the ```oe-core``` directory, initialize the build-environment:
```
source oe-init-build-env
```
* edit the OpenEmbedded layer configuration file ```conf/bblayers.conf``` to include
  some additional layers from 'meta-openembedded' as well as the `meta-vyos layer`.
  Your ```BBLAYERS``` variable should look as below, however you may have to adjust 
  your path
```
BBLAYERS ?= " \
  [your existing path]/oe-core/meta \
  ${TOPDIR}/../meta-openembedded/meta-oe \
  ${TOPDIR}/../meta-openembedded/meta-networking \
  ${TOPDIR}/../meta-openembedded/meta-python \
  ${TOPDIR}/../meta-openembedded/meta-perl \
  ${TOPDIR}/../meta-openembedded/meta-filesystems \
  ${TOPDIR}/../meta-vyos \
  "
```
* add the following line to the OpenEmbedded config file ```conf/local.conf``` to 
  to build a 'vyos' distro. This line can be added anywhere in the config file:
```
DISTRO ?= "vyos"
```
* you are now ready to start the build process. From the ```oe-core/build``` directory,
  enter:
```
bitbake vyos-image
```
* When done (likely after 45 minutes or more), an image will be in the
  ```oe-core/build/tmp-glibc/deploy/image/qemux86``` directory. It can be started by entering
```
runqemu
```

## Acknowledgements:

Credit goes to the VyOS team for contributing and maintaining such a great router platform! Kudos 
to Kim Hagen for having ported VyOS to the ClearFog platform and thereby inspiring this project!
