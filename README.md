# User instructions
## I just want to grab latest files for stardict

`git clone --depth 1 https://github.com/sanskrit-coders/stardict-sanskrit.git`

# Developer instructions
## Initial  setup.
* Extract the latest stardict tools package (<https://sourceforge.net/projects/stardict-4/files/3.0.1/stardict-tools-3.0.1.tar.bz2/download>) in ~/stardict/tools directory. Then do `cd stardict/tools` and build it (Run `./configure` and `make` as described in the INSTALL file in the directory - but don't remove the compiled binaries from the src directory.).
  * A shortcut if you are running Linux on a 64 bit computer: Just `git clone --depth 1 https://github.com/sanskrit-coders/stardict` in your home directory.
* Clone <https://github.com/sanskrit-coders/sanskritnlpjava> in your home directory.
* Clone this repo. (You can use `git clone --depth 1 https://github.com/sanskrit-coders/stardict-sanskrit.git` to get just the latest files.)

## Update dictionary files
With `sa-vyAkaraNa/laghu-kaumudi/laghu-kaumudi.babylon` as an example:
* If the scripts have changed in some way, do `rm -r /tmp/scala*`. (You can do this anyway - won't hurt.)
* Update the babylon file.
* Change directory to sa-vyAkaraNa.
* Run: make DICTS='laghu-kaumudi'. This will run the commands listed in sa-vyAkaraNa/makefile sequentially. All dictionaries and tars under sa-vyAkaraNa will be rebuilt as of 20160321.
* Watch out for errors and warnings.
* If you are satisfied with the output, add (preferably only the files you intended to change), commit and push. If not, open an issue.

### To force update dictionary files
Sometimes the above may fail due to the script being fooled by the timestamps into thinking that no updates are required. To force updates in such cases (With `sa-vyAkaraNa/laghu-kaumudi/laghu-kaumudi.babylon` as an example):
* Delete these files: `sa-vyAkaraNa/laghu-kaumudi/*.babylon sa-vyAkaraNa/laghu-kaumudi/*.ifo sa-vyAkaraNa/tars/laghu-kaumudi*.tar.gz`
* Redo the process described above.

## Other notes
Recipe to convert decompiled en-head dictionaries from ajita to sa-head dictionaries: ^.+\t(.+?)\.  should be replaced by \1\t
