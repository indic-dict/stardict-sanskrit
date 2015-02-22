import subprocess

for i in range(1,451):
  url = "http://sanskrit.jnu.ac.in/tinanta/tinanta.jsp?t=%d" % i
  cmd = "lynx -dump " + url + "> ~/tinanta/%d" % i
  print cmd
  subprocess.call(cmd, shell=True)
