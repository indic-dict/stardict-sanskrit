# Dictionary user instructions
## Grab latest files to use for with my stardict compatible program.
Options:
* Just download and extract [all_dicts_stardict_sanskrit.tar.gz](https://archive.org/download/stardict_collections/all_dicts_stardict_sanskrit.tar.gz)
* `git clone --depth 1 https://github.com/sanskrit-coders/stardict-sanskrit.git`

## Dictionary program recommendations and download help for mobile devices
* <https://sites.google.com/site/sanskritcode/dictionaries>

# Dictionary developer instructions
## Initial  setup.
* Extract the latest stardict tools package (<https://sourceforge.net/projects/stardict-4/files/3.0.1/stardict-tools-3.0.1.tar.bz2/download>) in ~/stardict/tools directory. Then do `cd stardict/tools` and build it (Run `./configure` and `make` as described in the INSTALL file in the directory - but don't remove the compiled binaries from the src directory.).
  * A shortcut if you are running Linux on a 64 bit computer: Just `git clone --depth 1 https://github.com/sanskrit-coders/stardict` in your home directory.
* Clone this repo. (You can use `git clone --depth 1 https://github.com/sanskrit-coders/stardict-sanskrit.git` to get just the latest files.)

## Update dictionary files
With `sa-sanskritnlp.vyAkaraNa.vyAkaraNa/laghu-kaumudi/laghu-kaumudi.babylon` as an example:
* If the scripts have changed in some way (You can do this anyway - won't hurt.)
	* do `rm -r /tmp/scala*`.
	* Update the repositories mentioned in the Initial setup section.
		* An easy way to do this is to run: `~/stardict-sanskrit/sync_all_repos.sh "git pull"`
* Update the babylon file.
* Change directory to sa-sanskritnlp.vyAkaraNa.vyAkaraNa.
* Run: make DICTS='laghu-kaumudi'. This will run the commands listed in sa-sanskritnlp.vyAkaraNa.vyAkaraNa/makefile sequentially. All dictionaries and tars under sa-sanskritnlp.vyAkaraNa.vyAkaraNa will be rebuilt as of 20160321.
* Watch out for errors and warnings.
* If you are satisfied with the output, add (preferably only the files you intended to change), commit and push. If not, open an issue.
* [Optional but preferred] Create a new release, and use those files!
  * Creating a new release ( [guide](https://help.github.com/articles/creating-releases/)).
    * Just drag and drop the relevant tar.gz files as release artifacts.
    * Make sure to add ALL the tar.gz files under the directory where you run the `make tarlist` command described below.
  * Example invocation, if the release you create is `2017-04-14`:
    * `make tarlist URL="https://github.com/sanskrit-coders/stardict-sanskrit/releases/download/2017-04-14/"`
    * You can get the URL portion above from the download URL of any artifact in the release page.
  * Remove tars once they're moved to release and you are satisfied that all is well. They can just be deleted by the contributor without checking them in (+ git rm for older files).
  * Reason why this is preferred:
    * Helps track dictionary popularity and downloads (see [this issue](https://github.com/sanskrit-coders/stardict-dictionary-updater/issues/12) ).
    * This is likely to result in fewer download problems as downloading from release rather than "raw" might be more robust.
* Update the all_dicts file.
  * Compress all the necessary files with `make tarall` invoked in the root directory.
  * Upload to archive (example: [all_dicts_stardict_sanskrit.tar.gz](https://archive.org/download/stardict_collections/all_dicts_stardict_sanskrit.tar.gz)).

### To force update dictionary files
Sometimes the above may fail due to the script being fooled by the timestamps into thinking that no updates are required. To force updates in such cases (With `sa-sanskritnlp.vyAkaraNa.vyAkaraNa/laghu-kaumudi/laghu-kaumudi.babylon` as an example):
* Delete these files: `sa-sanskritnlp.vyAkaraNa.vyAkaraNa/laghu-kaumudi/*.babylon sa-sanskritnlp.vyAkaraNa.vyAkaraNa/laghu-kaumudi/*.ifo sa-sanskritnlp.vyAkaraNa.vyAkaraNa/tars/laghu-kaumudi*.tar.gz`
* Redo the process described above.

## Dictionary popularity metrics
* For properly *release*ed dictionaries, one can get stats as [shown here](http://mmilidoni.github.io/github-downloads-count/).

## Other notes
Recipe to convert decompiled en-head dictionaries from ajita to sa-head dictionaries: ^.+\t(.+?)\.  should be replaced by \1\t

## Cologne to babylon convertor
https://github.com/sanskrit-lexicon/cologne-stardict - make_babylon.py is the workhorse.

# Dictionary and packaging software developer instructions
## Update /bin used by dictionary users
Use intellij to generate the bin/artifacts/stardict-sanskrit.jar artifact, which is used by dictionary developers during packaging. 

## Deployment
* Regarding **maven targets**:
  * You can set up a maven goal in intellij as well.
  * In intellij: Don't be fooled by weird messages in the Run widget - look at the messages widget.

## Releasing to maven.
* *TODO*: This is currently not working. 
* Note that we're using appengine-maven-plugin in <pom.xml>, and credentials stored in settings.xml (<-- not to be checked in) .
* Deploy snapshot artifacts into repository <https://oss.sonatype.org/content/repositories/snapshots/com/github/sanskrit-coders/sanskritnlp>.
  * Version number ends with -SNAPSHOT. Eg. 1.0-SNAPSHOT
  * Build target: clean deploy.
  * intellij target name: "mvn deploy".
* Deploy release artifacts into the [staging repository](https://oss.sonatype.org/content/repositories/releases/com/github/sanskrit-coders/sanskritnlp/) and [here](http://repo1.maven.org/maven2/com/github/sanskrit-coders/sanskritnlp/) :
  * Repeat the same with a non snapshot version number.
* Releasing to central (if it does not automatically happen):
  * Notes: <http://central.sonatype.org/pages/releasing-the-deployment.html>
  * Artifacts can be examined on Sonatype [here](https://oss.sonatype.org/#nexus-search;quick~sanskrit) and released - if the staging repository is visible there. Otherwise, it may already be deployed in central!
  * Maven target can be used: nexus-staging:release . There is an intellij target of the same name.
  * "After you successfully release, your component will be published to Central, typically within 10 minutes, though updates to search.maven.org can take up to two hours."
* Project was created under Sonatype:  [here](https://issues.sonatype.org/browse/OSSRH-29183) .

