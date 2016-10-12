Blockly.Blocks.Manager = {
    datatype: '[["String", "string"], ["Date", "date"], ["Number", "number"]]',
    workspaceContainer: {},
    ws: {},
    changeListeners: [],
    allBlocks: {
        root: {
            children: []
        },
        delNode: function(id) {
            var node = this.root[id];
            this.root[id] = {};
            return node.children;
        },
        addNode: function(obj, id, dataType, variableName, parent) {
            if (parent === undefined)
                this.root.children.push(id);
            else
                this.root[parent.id].children.push(id);
            this.root[id] = {
                'id': id,
                'obj': obj,
                'dataType': dataType,
                'variableName': variableName,
                'parent': parent,
                'children': []
            };
            return this.root[id];
        },
        typeChange: function(id, olddatatype, newdatatype) {
            if (oldtype === newtype) return;
            if (this.root[id].datatype === olddatatype) {
                this.root[id].datatype = newdatatype;
            }
            return this.root[id].children;
        },
        variableNameChange: function(id, oldName, newName) {
            if (oldName === newName) return;
            if (this.root[id].variableName === oldName) {
                this.root[id].variableName = newName;
            }
            return this.root[id].children;
        },
        getSupportedOperands: function(dataType) {
            var result = [];
            var key;
            if (!dataType) {
                for (key in this.root) {
                    try {
                        result.push(this.root[key].id);
                    } catch (e) {}
                }
            } else {
                for (key in this.root) {
                    try {
                        if (this.root[key].dataType === dataType) {
                            result.push(this.root[key].id);
                        }
                    } catch (e) {}
                }
            }
            return result;
        }
    },
    changeListener: function(event) {
        if (event.internal !== undefined) {
            //ignore
        }
        switch (event.type) {
            case Blockly.Events.CHANGE:
                switch (event.element) {
                    case 'comment':
                        break;
                    case 'collapsed':
                        break;
                    case 'disabled':
                        break;
                    case 'inline':
                        break;
                    case 'mutate':
                        break;
                }
                break;
            case Blockly.Events.CREATE:
                //debugger;
                var block = this.ws.getBlockById(event.blockId);
                this.allBlocks.addNode(block, event.blockId, block.type);
                break;
            case Blockly.Events.DELETE:
            //debugger;
                this.allBlocks.delNode(block.blockId);
                //TODO del all children recursively
                break;
            case Blockly.Events.MOVE:
                if (event.newParentId) {//attached
                    block = this.ws.getBlockById(event.blockId);
                    var blockP = this.ws.getBlockById(event.newParentId);
                    this.allBlocks.addNode(block, event.blockId, block.type, blockP);
                    //TODO create new block  & add it to either transform block or store
                }
                break;
            case Blockly.Events.UI:
                break;
        }
    },
    registerChangeEvent: function(block, type) {

    },
    unRegisterChangeEvent: function(block, type) {

    },
    attachBlock: function(child, parent) {
        this._executeEvent({
            type: "move",
            blockId: child.id,
            newParentId: parent.id,
            internal: true,
            toJson: function() {
                return {
                    type: "move",
                    blockId: child.id,
                    newParentId: parent.id
                };
            }
        });
    },
    deattachBlock: function(child, parent) {

    },
    getSupportedOperands: function(block) {
        return this.allBlocks.getSupportedOperands((block === undefined)? null : block.getDataType);
    },
    getSupportedOperators: function(block) {
        return block.getSupportedOperators();
    },
    getSupportedDataTypes: function(block) {
        return this.dataType;
    },
    _executeEvent: function(event) {
        Blockly.Events.fromJson(event.toJson(), ws)
            .run(true);
    }
};
var bbm = Blockly.Blocks.Manager;
Blockly.Blocks.Manager.ws = Blockly.getMainWorkspace();
//Blockly.Blocks.Manager.workspaceContainer = Blockly.inject('workspaceDiv', {});
Blockly.getMainWorkspace().addChangeListener(Blockly.Blocks.Manager.changeListener.bind(bbm));
