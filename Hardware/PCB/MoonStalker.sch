EESchema Schematic File Version 2
LIBS:power
LIBS:device
LIBS:transistors
LIBS:conn
LIBS:linear
LIBS:regul
LIBS:74xx
LIBS:cmos4000
LIBS:adc-dac
LIBS:memory
LIBS:xilinx
LIBS:microcontrollers
LIBS:dsp
LIBS:microchip
LIBS:analog_switches
LIBS:motorola
LIBS:texas
LIBS:intel
LIBS:audio
LIBS:interface
LIBS:digital-audio
LIBS:philips
LIBS:display
LIBS:cypress
LIBS:siliconi
LIBS:opto
LIBS:atmel
LIBS:contrib
LIBS:valves
LIBS:MoonStalker
LIBS:MoonStalker-cache
EELAYER 25 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 1 1
Title "MoonStalker Drive Unit"
Date "2016-01-08"
Rev "0.1"
Comp "Koza d.o.o."
Comment1 "It is very nice"
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
$Comp
L Arduino_Micro U5
U 1 1 568D949D
P 7150 4050
F 0 "U5" H 7150 3100 60  0000 C CNN
F 1 "Arduino_Micro" H 7150 5100 60  0000 C CNN
F 2 "" H 7200 3800 60  0000 C CNN
F 3 "" H 7200 3800 60  0000 C CNN
	1    7150 4050
	1    0    0    -1  
$EndComp
$Comp
L Pololu_DRV8825 U1
U 1 1 568D968D
P 2250 1900
F 0 "U1" H 2250 1400 60  0000 C CNN
F 1 "Pololu_DRV8825" H 2250 2400 60  0000 C CNN
F 2 "" H 2350 1600 60  0000 C CNN
F 3 "" H 2350 1600 60  0000 C CNN
	1    2250 1900
	1    0    0    -1  
$EndComp
$Comp
L Pololu_DRV8825 U2
U 1 1 568D96DA
P 2250 3600
F 0 "U2" H 2250 3100 60  0000 C CNN
F 1 "Pololu_DRV8825" H 2250 4100 60  0000 C CNN
F 2 "" H 2350 3300 60  0000 C CNN
F 3 "" H 2350 3300 60  0000 C CNN
	1    2250 3600
	1    0    0    -1  
$EndComp
$Comp
L HC-05_Bluetooth_Radio U4
U 1 1 568EE7E0
P 8400 1400
F 0 "U4" V 8850 1350 60  0000 C CNN
F 1 "HC-05_Bluetooth_Radio" V 8000 1450 60  0000 C CNN
F 2 "" H 8500 1250 60  0000 C CNN
F 3 "" H 8500 1250 60  0000 C CNN
	1    8400 1400
	0    1    1    0   
$EndComp
$Comp
L TEL3-1211 U3
U 1 1 568EF545
P 2250 6050
F 0 "U3" H 2250 5450 60  0000 C CNN
F 1 "TEL3-1211" H 2250 6600 60  0000 C CNN
F 2 "" H 1850 6000 60  0000 C CNN
F 3 "" H 1850 6000 60  0000 C CNN
	1    2250 6050
	1    0    0    -1  
$EndComp
$Comp
L VCC #PWR01
U 1 1 568EF868
P 8300 3700
F 0 "#PWR01" H 8300 3550 50  0001 C CNN
F 1 "VCC" H 8300 3850 50  0000 C CNN
F 2 "" H 8300 3700 50  0000 C CNN
F 3 "" H 8300 3700 50  0000 C CNN
	1    8300 3700
	1    0    0    -1  
$EndComp
$Comp
L VCC #PWR02
U 1 1 568EF93E
P 7300 1150
F 0 "#PWR02" H 7300 1000 50  0001 C CNN
F 1 "VCC" H 7300 1300 50  0000 C CNN
F 2 "" H 7300 1150 50  0000 C CNN
F 3 "" H 7300 1150 50  0000 C CNN
	1    7300 1150
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR03
U 1 1 568EF95C
P 7300 1350
F 0 "#PWR03" H 7300 1100 50  0001 C CNN
F 1 "GND" H 7300 1200 50  0000 C CNN
F 2 "" H 7300 1350 50  0000 C CNN
F 3 "" H 7300 1350 50  0000 C CNN
	1    7300 1350
	1    0    0    -1  
$EndComp
Text GLabel 7700 1450 0    47   Output ~ 0
TXD
Text GLabel 7700 1550 0    47   Input ~ 0
RXD
Text GLabel 6050 3500 0    47   Input ~ 0
TXD
Text GLabel 6050 3400 0    47   Output ~ 0
RXD
NoConn ~ 7850 1650
NoConn ~ 7850 1150
NoConn ~ 1550 5750
NoConn ~ 1550 5850
NoConn ~ 2950 5750
NoConn ~ 2950 5850
$Comp
L GND #PWR04
U 1 1 5691B203
P 1250 6250
F 0 "#PWR04" H 1250 6000 50  0001 C CNN
F 1 "GND" H 1250 6100 50  0000 C CNN
F 2 "" H 1250 6250 50  0000 C CNN
F 3 "" H 1250 6250 50  0000 C CNN
	1    1250 6250
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR05
U 1 1 5691B221
P 3350 6250
F 0 "#PWR05" H 3350 6000 50  0001 C CNN
F 1 "GND" H 3350 6100 50  0000 C CNN
F 2 "" H 3350 6250 50  0000 C CNN
F 3 "" H 3350 6250 50  0000 C CNN
	1    3350 6250
	1    0    0    -1  
$EndComp
NoConn ~ 7850 3800
NoConn ~ 7850 4700
$Comp
L CP C1
U 1 1 5691B4FD
P 3800 6750
F 0 "C1" H 3825 6850 50  0000 L CNN
F 1 "1000uF" H 3825 6650 50  0000 L CNN
F 2 "" H 3838 6600 50  0000 C CNN
F 3 "" H 3800 6750 50  0000 C CNN
	1    3800 6750
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR06
U 1 1 5691B598
P 3800 6900
F 0 "#PWR06" H 3800 6650 50  0001 C CNN
F 1 "GND" H 3800 6750 50  0000 C CNN
F 2 "" H 3800 6900 50  0000 C CNN
F 3 "" H 3800 6900 50  0000 C CNN
	1    3800 6900
	1    0    0    -1  
$EndComp
$Comp
L VCC #PWR07
U 1 1 5691B750
P 4050 6550
F 0 "#PWR07" H 4050 6400 50  0001 C CNN
F 1 "VCC" H 4050 6700 50  0000 C CNN
F 2 "" H 4050 6550 50  0000 C CNN
F 3 "" H 4050 6550 50  0000 C CNN
	1    4050 6550
	1    0    0    -1  
$EndComp
NoConn ~ 7850 3400
$Comp
L GND #PWR08
U 1 1 5691BAE4
P 8100 3500
F 0 "#PWR08" H 8100 3250 50  0001 C CNN
F 1 "GND" H 8100 3350 50  0000 C CNN
F 2 "" H 8100 3500 50  0000 C CNN
F 3 "" H 8100 3500 50  0000 C CNN
	1    8100 3500
	1    0    0    -1  
$EndComp
NoConn ~ 7850 3200
NoConn ~ 6450 3200
NoConn ~ 7850 3300
$Comp
L GND #PWR09
U 1 1 5691BB66
P 5850 3700
F 0 "#PWR09" H 5850 3450 50  0001 C CNN
F 1 "GND" H 5850 3550 50  0000 C CNN
F 2 "" H 5850 3700 50  0000 C CNN
F 3 "" H 5850 3700 50  0000 C CNN
	1    5850 3700
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR010
U 1 1 5691BC7E
P 3100 2250
F 0 "#PWR010" H 3100 2000 50  0001 C CNN
F 1 "GND" H 3100 2100 50  0000 C CNN
F 2 "" H 3100 2250 50  0000 C CNN
F 3 "" H 3100 2250 50  0000 C CNN
	1    3100 2250
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR011
U 1 1 5691BCC6
P 3300 1550
F 0 "#PWR011" H 3300 1300 50  0001 C CNN
F 1 "GND" H 3300 1400 50  0000 C CNN
F 2 "" H 3300 1550 50  0000 C CNN
F 3 "" H 3300 1550 50  0000 C CNN
	1    3300 1550
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR012
U 1 1 5691BD33
P 3300 3250
F 0 "#PWR012" H 3300 3000 50  0001 C CNN
F 1 "GND" H 3300 3100 50  0000 C CNN
F 2 "" H 3300 3250 50  0000 C CNN
F 3 "" H 3300 3250 50  0000 C CNN
	1    3300 3250
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR013
U 1 1 5691BD53
P 3100 3950
F 0 "#PWR013" H 3100 3700 50  0001 C CNN
F 1 "GND" H 3100 3800 50  0000 C CNN
F 2 "" H 3100 3950 50  0000 C CNN
F 3 "" H 3100 3950 50  0000 C CNN
	1    3100 3950
	1    0    0    -1  
$EndComp
$Comp
L CONN_01X02 P3
U 1 1 5692D6C6
P 3900 2350
F 0 "P3" H 3900 2500 50  0000 C CNN
F 1 "CONN_01X02" V 4000 2350 50  0000 C CNN
F 2 "" H 3900 2350 50  0000 C CNN
F 3 "" H 3900 2350 50  0000 C CNN
	1    3900 2350
	1    0    0    -1  
$EndComp
$Comp
L CONN_01X02 P2
U 1 1 5692D6FD
P 3900 1800
F 0 "P2" H 3900 1950 50  0000 C CNN
F 1 "CONN_01X02" V 4000 1800 50  0000 C CNN
F 2 "" H 3900 1800 50  0000 C CNN
F 3 "" H 3900 1800 50  0000 C CNN
	1    3900 1800
	1    0    0    -1  
$EndComp
$Comp
L CONN_01X02 P5
U 1 1 5692DA04
P 3950 4050
F 0 "P5" H 3950 4200 50  0000 C CNN
F 1 "CONN_01X02" V 4050 4050 50  0000 C CNN
F 2 "" H 3950 4050 50  0000 C CNN
F 3 "" H 3950 4050 50  0000 C CNN
	1    3950 4050
	1    0    0    -1  
$EndComp
$Comp
L CONN_01X02 P4
U 1 1 5692DA0A
P 3950 3500
F 0 "P4" H 3950 3650 50  0000 C CNN
F 1 "CONN_01X02" V 4050 3500 50  0000 C CNN
F 2 "" H 3950 3500 50  0000 C CNN
F 3 "" H 3950 3500 50  0000 C CNN
	1    3950 3500
	1    0    0    -1  
$EndComp
Text Notes 1850 1300 0    60   ~ 0
HORIZONTAL DRIVER
Text Notes 1900 3000 0    60   ~ 0
VERTICAL DRIVER
$Comp
L GND #PWR014
U 1 1 5692E26D
P 1450 1550
F 0 "#PWR014" H 1450 1300 50  0001 C CNN
F 1 "GND" H 1450 1400 50  0000 C CNN
F 2 "" H 1450 1550 50  0000 C CNN
F 3 "" H 1450 1550 50  0000 C CNN
	1    1450 1550
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR015
U 1 1 5692E295
P 1450 3250
F 0 "#PWR015" H 1450 3000 50  0001 C CNN
F 1 "GND" H 1450 3100 50  0000 C CNN
F 2 "" H 1450 3250 50  0000 C CNN
F 3 "" H 1450 3250 50  0000 C CNN
	1    1450 3250
	1    0    0    -1  
$EndComp
Text GLabel 1450 2150 0    47   Input ~ 0
HORIZ_STEP
Text GLabel 1400 2250 0    47   Input ~ 0
HORIZ_DIR
Wire Wire Line
	7300 1350 7850 1350
Wire Wire Line
	7300 1150 7300 1250
Wire Wire Line
	7300 1250 7850 1250
Wire Wire Line
	7700 1450 7850 1450
Wire Wire Line
	7700 1550 7850 1550
Wire Wire Line
	6050 3400 6450 3400
Wire Wire Line
	6050 3500 6450 3500
Wire Wire Line
	2950 5400 2950 5650
Wire Wire Line
	950  5400 3900 5400
Wire Wire Line
	1550 5400 1550 5650
Wire Wire Line
	2950 6250 3350 6250
Wire Wire Line
	1250 6250 1550 6250
Wire Wire Line
	2950 6350 3150 6350
Wire Wire Line
	3150 6350 3150 6850
Wire Wire Line
	3150 6850 1450 6850
Wire Wire Line
	1450 6850 1450 6350
Wire Wire Line
	1450 6350 1550 6350
Wire Wire Line
	7850 3700 8300 3700
Wire Wire Line
	3800 6550 3800 6600
Connection ~ 3150 6550
Connection ~ 3800 6550
Wire Wire Line
	7850 3500 8100 3500
Wire Wire Line
	5850 3700 6450 3700
Wire Wire Line
	2800 2250 3100 2250
Wire Wire Line
	2800 3950 3100 3950
Wire Wire Line
	2800 3450 3750 3450
Wire Wire Line
	2800 3550 3750 3550
Wire Wire Line
	2800 3650 3700 3650
Wire Wire Line
	3700 3650 3700 4000
Wire Wire Line
	3700 4000 3750 4000
Wire Wire Line
	2800 3750 3600 3750
Wire Wire Line
	3600 3750 3600 4100
Wire Wire Line
	3600 4100 3750 4100
Wire Wire Line
	2800 1750 3700 1750
Wire Wire Line
	2800 1850 3700 1850
Wire Wire Line
	2800 1950 3600 1950
Wire Wire Line
	3600 1950 3600 2300
Wire Wire Line
	3600 2300 3700 2300
Wire Wire Line
	2800 2050 3500 2050
Wire Wire Line
	3500 2050 3500 2400
Wire Wire Line
	3500 2400 3700 2400
Wire Wire Line
	1450 1550 1700 1550
Wire Wire Line
	1550 1550 1550 1850
Wire Wire Line
	1550 1850 1700 1850
Wire Wire Line
	1550 1750 1700 1750
Connection ~ 1550 1750
Wire Wire Line
	1550 1650 1700 1650
Connection ~ 1550 1650
Connection ~ 1550 1550
Wire Wire Line
	1450 3250 1700 3250
Wire Wire Line
	1550 3250 1550 3550
Wire Wire Line
	1550 3550 1700 3550
Wire Wire Line
	1550 3450 1700 3450
Connection ~ 1550 3450
Wire Wire Line
	1550 3350 1700 3350
Connection ~ 1550 3350
Connection ~ 1550 3250
Wire Wire Line
	1450 2150 1700 2150
Wire Wire Line
	1400 2250 1700 2250
Text GLabel 1400 3850 0    47   Input ~ 0
VERT_STEP
Text GLabel 1350 3950 0    47   Input ~ 0
VERT_DIR
Wire Wire Line
	1400 3850 1700 3850
Wire Wire Line
	1350 3950 1700 3950
Text GLabel 2900 2150 2    47   Output ~ 0
HORIZ_FAULT
Wire Wire Line
	2800 2150 2900 2150
Text GLabel 2950 3850 2    47   Output ~ 0
VERT_FAULT
Wire Wire Line
	2800 3850 2950 3850
Text GLabel 1500 1950 0    47   Input ~ 0
HORIZ_RESET
Text GLabel 1500 2050 0    47   Input ~ 0
HORIZ_SLEEP
Wire Wire Line
	1500 1950 1700 1950
Wire Wire Line
	1500 2050 1700 2050
Text GLabel 1450 3650 0    47   Input ~ 0
VERT_RESET
Text GLabel 1450 3750 0    47   Input ~ 0
VERT_SLEEP
Wire Wire Line
	1450 3650 1700 3650
Wire Wire Line
	1450 3750 1700 3750
Text GLabel 6400 3800 0    47   Output ~ 0
HORIZ_STEP
Text GLabel 6400 3900 0    47   Output ~ 0
HORIZ_DIR
Wire Wire Line
	6400 3800 6450 3800
Wire Wire Line
	6400 3900 6450 3900
Text GLabel 6400 4100 0    47   Output ~ 0
VERT_STEP
Text GLabel 6400 4200 0    47   Output ~ 0
VERT_DIR
Wire Wire Line
	6400 4100 6450 4100
Wire Wire Line
	6400 4200 6450 4200
Text GLabel 6400 4300 0    47   Output ~ 0
HORIZ_RESET
Text GLabel 6400 4400 0    47   Output ~ 0
HORIZ_SLEEP
Text GLabel 6400 4500 0    47   Output ~ 0
VERT_RESET
Text GLabel 6400 4600 0    47   Output ~ 0
VERT_SLEEP
Wire Wire Line
	6400 4300 6450 4300
Wire Wire Line
	6400 4400 6450 4400
Wire Wire Line
	6400 4500 6450 4500
Wire Wire Line
	6400 4600 6450 4600
Text GLabel 6400 4700 0    47   Input ~ 0
HORIZ_FAULT
Text GLabel 6350 4800 0    47   Input ~ 0
VERT_FAULT
Wire Wire Line
	6400 4700 6450 4700
Wire Wire Line
	6350 4800 6450 4800
Wire Wire Line
	3150 6550 4050 6550
$Comp
L CONN_01X02 P1
U 1 1 5692F849
P 750 5450
F 0 "P1" H 750 5600 50  0000 C CNN
F 1 "CONN_01X02" V 850 5450 50  0000 C CNN
F 2 "" H 750 5450 50  0000 C CNN
F 3 "" H 750 5450 50  0000 C CNN
	1    750  5450
	-1   0    0    1   
$EndComp
Connection ~ 1550 5400
Wire Wire Line
	950  5500 1250 5500
$Comp
L R R1
U 1 1 5692FAD4
P 3900 5550
F 0 "R1" V 3980 5550 50  0000 C CNN
F 1 "3k3" V 3900 5550 50  0000 C CNN
F 2 "" V 3830 5550 50  0000 C CNN
F 3 "" H 3900 5550 50  0000 C CNN
	1    3900 5550
	1    0    0    -1  
$EndComp
$Comp
L R R2
U 1 1 5692FB07
P 3900 5900
F 0 "R2" V 3980 5900 50  0000 C CNN
F 1 "1k" V 3900 5900 50  0000 C CNN
F 2 "" V 3830 5900 50  0000 C CNN
F 3 "" H 3900 5900 50  0000 C CNN
	1    3900 5900
	1    0    0    -1  
$EndComp
Wire Wire Line
	3900 5700 3900 5750
$Comp
L GND #PWR016
U 1 1 5692FBB8
P 3900 6150
F 0 "#PWR016" H 3900 5900 50  0001 C CNN
F 1 "GND" H 3900 6000 50  0000 C CNN
F 2 "" H 3900 6150 50  0000 C CNN
F 3 "" H 3900 6150 50  0000 C CNN
	1    3900 6150
	1    0    0    -1  
$EndComp
Wire Wire Line
	3900 6050 3900 6150
Connection ~ 2950 5400
Text GLabel 4350 5700 2    47   UnSpc ~ 0
VBATT
Wire Wire Line
	3900 5700 4350 5700
Text GLabel 8050 4500 2    47   UnSpc ~ 0
VBATT
Wire Wire Line
	7850 4500 8050 4500
NoConn ~ 6450 4000
$Comp
L VCC #PWR017
U 1 1 56930065
P 5800 3600
F 0 "#PWR017" H 5800 3450 50  0001 C CNN
F 1 "VCC" H 5800 3750 50  0000 C CNN
F 2 "" H 5800 3600 50  0000 C CNN
F 3 "" H 5800 3600 50  0000 C CNN
	1    5800 3600
	1    0    0    -1  
$EndComp
Wire Wire Line
	5800 3600 6450 3600
Wire Wire Line
	7850 3600 7950 3600
Wire Wire Line
	7950 3600 7950 3700
Connection ~ 7950 3700
NoConn ~ 7850 3900
NoConn ~ 7850 4000
NoConn ~ 7850 4100
NoConn ~ 7850 4200
NoConn ~ 7850 4300
NoConn ~ 7850 4400
NoConn ~ 7850 4600
NoConn ~ 7850 4800
$Comp
L CP C4
U 1 1 56930434
P 3600 5650
F 0 "C4" H 3625 5750 50  0000 L CNN
F 1 "1000uF" H 3625 5550 50  0000 L CNN
F 2 "" H 3638 5500 50  0000 C CNN
F 3 "" H 3600 5650 50  0000 C CNN
	1    3600 5650
	1    0    0    -1  
$EndComp
Wire Wire Line
	3600 5300 3600 5500
Connection ~ 3600 5400
$Comp
L GND #PWR018
U 1 1 5693077E
P 3600 5850
F 0 "#PWR018" H 3600 5600 50  0001 C CNN
F 1 "GND" H 3600 5700 50  0000 C CNN
F 2 "" H 3600 5850 50  0000 C CNN
F 3 "" H 3600 5850 50  0000 C CNN
	1    3600 5850
	1    0    0    -1  
$EndComp
Wire Wire Line
	3600 5800 3600 5850
$Comp
L CP C2
U 1 1 5693089A
P 3050 1400
F 0 "C2" H 3075 1500 50  0000 L CNN
F 1 "100uF" H 3075 1300 50  0000 L CNN
F 2 "" H 3088 1250 50  0000 C CNN
F 3 "" H 3050 1400 50  0000 C CNN
	1    3050 1400
	1    0    0    -1  
$EndComp
Wire Wire Line
	2800 1650 3200 1650
Wire Wire Line
	3200 1650 3200 1550
Wire Wire Line
	3200 1550 3300 1550
Wire Wire Line
	2800 3350 3200 3350
Wire Wire Line
	3200 3350 3200 3250
Wire Wire Line
	3200 3250 3300 3250
$Comp
L CP C3
U 1 1 56930AE0
P 3050 3100
F 0 "C3" H 3075 3200 50  0000 L CNN
F 1 "100uF" H 3075 3000 50  0000 L CNN
F 2 "" H 3088 2950 50  0000 C CNN
F 3 "" H 3050 3100 50  0000 C CNN
	1    3050 3100
	1    0    0    -1  
$EndComp
Wire Wire Line
	2800 1550 2800 1250
Wire Wire Line
	2800 1250 3050 1250
Wire Wire Line
	2800 3250 2800 2950
Wire Wire Line
	2800 2950 3050 2950
$Comp
L VAA #PWR019
U 1 1 56930CF2
P 3600 5300
F 0 "#PWR019" H 3600 5150 50  0001 C CNN
F 1 "VAA" H 3600 5450 50  0000 C CNN
F 2 "" H 3600 5300 50  0000 C CNN
F 3 "" H 3600 5300 50  0000 C CNN
	1    3600 5300
	1    0    0    -1  
$EndComp
$Comp
L VAA #PWR020
U 1 1 56930DB8
P 3050 2850
F 0 "#PWR020" H 3050 2700 50  0001 C CNN
F 1 "VAA" H 3050 3000 50  0000 C CNN
F 2 "" H 3050 2850 50  0000 C CNN
F 3 "" H 3050 2850 50  0000 C CNN
	1    3050 2850
	1    0    0    -1  
$EndComp
$Comp
L VAA #PWR021
U 1 1 56930E7C
P 3050 1150
F 0 "#PWR021" H 3050 1000 50  0001 C CNN
F 1 "VAA" H 3050 1300 50  0000 C CNN
F 2 "" H 3050 1150 50  0000 C CNN
F 3 "" H 3050 1150 50  0000 C CNN
	1    3050 1150
	1    0    0    -1  
$EndComp
Wire Wire Line
	3050 1250 3050 1150
Wire Wire Line
	3050 2950 3050 2850
Wire Wire Line
	3050 1550 3050 1650
Connection ~ 3050 1650
Wire Wire Line
	3050 3250 3050 3350
Connection ~ 3050 3350
NoConn ~ 6450 3300
Text Notes 5200 5900 2    79   ~ 0
TODO:
Text Notes 7450 6050 2    79   ~ 0
Add DC-DC input and output filtering caps\n
Text Notes 6950 6200 2    79   ~ 0
Fix DC-DC power output ERC error
$Comp
L GND #PWR022
U 1 1 5692E8AB
P 1550 6550
F 0 "#PWR022" H 1550 6300 50  0001 C CNN
F 1 "GND" H 1550 6400 50  0000 C CNN
F 2 "" H 1550 6550 50  0000 C CNN
F 3 "" H 1550 6550 50  0000 C CNN
	1    1550 6550
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR023
U 1 1 5692E8DF
P 2950 6550
F 0 "#PWR023" H 2950 6300 50  0001 C CNN
F 1 "GND" H 2950 6400 50  0000 C CNN
F 2 "" H 2950 6550 50  0000 C CNN
F 3 "" H 2950 6550 50  0000 C CNN
	1    2950 6550
	1    0    0    -1  
$EndComp
Wire Wire Line
	1550 6450 1550 6550
Wire Wire Line
	2950 6450 2950 6550
Wire Wire Line
	1250 5500 1250 6250
$Comp
L PWR_FLAG #FLG024
U 1 1 5692ED49
P 1150 5200
F 0 "#FLG024" H 1150 5295 50  0001 C CNN
F 1 "PWR_FLAG" H 1150 5380 50  0000 C CNN
F 2 "" H 1150 5200 50  0000 C CNN
F 3 "" H 1150 5200 50  0000 C CNN
	1    1150 5200
	1    0    0    -1  
$EndComp
Wire Wire Line
	1150 5200 1150 5400
Connection ~ 1150 5400
$EndSCHEMATC
