 
# simple: Stores the numbers 2 and 3 as well as their sum.
# @author Ms. Datar
#	  Julia Biswas
# @version 15 April 2020

  .text 0x00400000
  .globl main

main:
  li $t0, 2 			#sets $t0 to 2
  li $t1, 3 			#sets $t1 to 3
  addu $t2, $t0, $t1 		# stores sum of $t0 and $t1 in $t2
  
  li $v0, 10 			#normal termination
  syscall
