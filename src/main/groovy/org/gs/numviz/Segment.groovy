package org.gs.numviz

import java.awt.Color
import java.awt.Point
import java.awt.geom.Point2D
import java.util.logging.Logger


/**
 * Segment represents a digit and is drawn as part of a circle.
 * When we use 10 Segments, every instance extends 36 deg, that is 0.2*&#960;rad.
 *
 */

class Segment {

    private int digit

    // how often does this digit occur in pairs?
    // how many connections will start or end in this segment?
    private int nrOfRequiredDigiNodes

    // what is the number of the next free digiNode
    private int nextFreeDigiNode = -1

    private double radius

    private Color color

    private double angleStart
    private double angleExtend

    // several "connection points" for lines
    public List<DigiNode> digiNode

    private static final Logger LOGGER = Logger.getLogger(Segment.class.getName())

    // implicit constructor to allow named parameters

    /**
     * returns the next free digiNode
     **/
    public int getNextFreeDigiNode() {

        assert nextFreeDigiNode <= digiNode.size(): "Segment $digit has no free digiNodes after $nextFreeDigiNode"
        return nextFreeDigiNode
    }

    /**
     * increments the pointer to next available digiNode
     */
    public void advanceToNextAvailableDigiNode() {

        assert nextFreeDigiNode < digiNode.size(): "cannot advance digiNode pointer, as segment $digit has no free digiNodes after $nextFreeDigiNode"

        nextFreeDigiNode += 1
    }

    /**
     * calculate the digiNodes for this Segment with the
     * <a href="http://www.mathopenref.com/coordparamcircle.html">parametric circle equation</a>:
     * x = radius * cos(t)    y = radius * sin(t) with t being the angle...
     * @return points in Segment, where lines will be attached
     */
    public void setUpDigiNodes() {

        // digit does not occur in number -> no digiNodes
        assert this.nrOfRequiredDigiNodes > 0: "setUpDigiNodes error: Segment[${this.digit}] cannot create $nrOfRequiredDigiNodes digiNodes"

        // as Lists start at index 0:
        digiNode = new ArrayList<DigiNode>(nrOfRequiredDigiNodes)

        // where to start attaching connections
        nextFreeDigiNode = 0

        double deltaAngle = deltaAngle(this.nrOfRequiredDigiNodes, angleExtend)

        println "will create ${nrOfRequiredDigiNodes} digiNodes with deltaAngle=${deltaAngle} for angleExtend=${angleExtend} and angleStart=${angleStart}"

        // for each digit to show, create one digiNode
        (1..nrOfRequiredDigiNodes).each { nrOfCurrentDigiNode ->
            // digiNode constructor is responsible for calculating Coordinates/Points
            DigiNode tmpNode = new DigiNode(angleForThisDigiNode(angleStart, deltaAngle, nrOfCurrentDigiNode),
                    radius)
            tmpNode.coordinate.mirrorAtXAxis()

            digiNode[nrOfCurrentDigiNode - 1] = tmpNode


        }


    }

    /**
     * what is the actual angle for this digiNode?
     */
    public static double angleForThisDigiNode(double angleStart, double deltaAngle, int nrOfCurrentDigiNode) {

        double theAngle = angleStart + deltaAngle * (nrOfCurrentDigiNode)
        return theAngle
    }

    /**
     * what is the delta-angle between digiNodes? Does NOT depend on starting angle,
     * only on angleExtend and nrOfDigiNodes
     * @param nrOfDigiNodes
     * @param angleExtend
     * @return delta-angle between digiNodes within this segment
     * for examples, {@link SegmentSpec # "DigiNodes are distributed evenly along Segment Zero"}:
     *
     */
    public static double deltaAngle(int nrOfDigiNodes, double angleExtend) {
        assert nrOfDigiNodes >= 0
        assert angleExtend >= 0
        return angleExtend / (Math.max(nrOfDigiNodes, 1) + 1)
    }

    /**
     * returns a readable version of this segment
     */
    public String toString() {
        return """Segment $digit requires $nrOfRequiredDigiNodes connections
      angleStart=${sprintf("%3.3fRAD (%3.3f°)", angleStart, Math.toDegrees(angleStart))}, extend=${sprintf("%3.3fRAD (%3.3f°)", angleExtend, Math.toDegrees(angleExtend))}
"""
    }
}

/*********************************************************************************
 The MIT License (MIT)

 Copyright (c) 2015 Dr. Gernot Starke

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.

 *********************************************************************************/
