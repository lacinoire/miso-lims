/*
 * Copyright (c) 2012. The Genome Analysis Centre, Norwich, UK
 * MISO project contacts: Robert Davey @ TGAC
 * *********************************************************************
 *
 * This file is part of MISO.
 *
 * MISO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MISO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MISO.  If not, see <http://www.gnu.org/licenses/>.
 *
 * *********************************************************************
 */

ListTarget.storage_location = {
  name: "Storage Locations",
  getUserManualUrl: function() {
    return Urls.external.userManual('freezers_and_rooms');
  },
  createUrl: function(config, projectId) {
    throw new Error("Storage locations must be specified statically.");
  },
  getQueryUrl: null,
  createBulkActions: function(config, projectId) {
    return !config.isAdmin ? [] : [ListUtils.createBulkDeleteAction(config.slug[0].toUpperCase() + config.slug.slice(1),
        "storagelocations", function(location) {
          return location.alias;
        })];
  },
  createStaticActions: function(config, projectId) {
    switch (config.slug) {
    case 'rooms':
      return [{
        "name": "Add",
        "handler": function() {
          Freezer.addRoomWithCallback(Utils.page.pageReload);
        }
      }];
    case 'freezers':
      return [{
        "name": "Add",
        "handler": function() {
          window.location = Urls.ui.freezers.create;
        }
      }];
    default:
      return [];
    }
  },
  createColumns: function(config, projectId) {
    var columns = [
        config.slug == "freezers" ? ListUtils.labelHyperlinkColumn("Alias", Urls.ui.freezers.edit, Utils.array.getId, "alias", 1, true) : {
          "sTitle": "Alias",
          "mData": "alias",
          "include": true,
          "iSortPriority": 1
        }, {
          "sTitle": "Identification Barcode",
          "mData": "identificationBarcode",
          "include": true,
          "iSortPriority": 0
        }, ];
    if (config.slug == 'freezers') {
      columns.push({
        "sTitle": "Status",
        "mData": "retired",
        "include": true,
        mRender: function(data, type, full) {
          if (type === 'display') {
            return data ? "Retired" : "Production";
          }
          return data;
        }
      });
      columns.push({
        "sTitle": "Map",
        "mData": "mapFilename",
        "include": true,
        "iSortPriority": 0,
        "mRender": function(data, type, full) {
          if (type === 'display' && data) {
            return "<a href=\"" + Urls.ui.freezerMaps.view(data, full.mapAnchor) + "\">View Map</a>";
          }
          return data || '';
        }
      });
    }
    return columns;
  }
};
